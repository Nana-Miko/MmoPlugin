package com.nana.mmoplugin.mmoplugin.util;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;


public class vectorUtil {
    /**
     * This handles non-unit vectors, with yaw and pitch instead of X,Y,Z angles.
     * <p>
     * Thanks to SexyToad!
     * <p>
     * 将一个非单位向量使用yaw和pitch来代替X, Y, Z的角旋转方式
     *
     * @param v            向量
     * @param yawDegrees   yaw的角度
     * @param pitchDegrees pitch的角度
     * @return
     */
    public static final Vector rotateVector(Vector v, float yawDegrees, float pitchDegrees) {
        double yaw = Math.toRadians(-1 * (yawDegrees + 90));
        double pitch = Math.toRadians(-pitchDegrees);

        double cosYaw = Math.cos(yaw);
        double cosPitch = Math.cos(pitch);
        double sinYaw = Math.sin(yaw);
        double sinPitch = Math.sin(pitch);

        double initialX, initialY, initialZ;
        double x, y, z;

        // Z_Axis rotation (Pitch)
        initialX = v.getX();
        initialY = v.getY();
        x = initialX * cosPitch - initialY * sinPitch;
        y = initialX * sinPitch + initialY * cosPitch;

        // Y_Axis rotation (Yaw)
        initialZ = v.getZ();
        initialX = x;
        z = initialZ * cosYaw - initialX * sinYaw;
        x = initialZ * sinYaw + initialX * cosYaw;

        return new Vector(x, y, z);
    }

    public static final void moveEntity(Entity entity, Location startLocation, Location endLocation){

        Vector vectorAB = endLocation.clone().subtract(startLocation).toVector();

        double vectorLength = vectorAB.length();
        vectorAB.normalize();

        for (double i = 0; i < vectorLength; i += 0.1){
            Vector vector = vectorAB.clone().multiply(i);
            startLocation.add(vector);
            entity.teleportAsync(startLocation);
            startLocation.subtract(vector);
        }

    }
    /**
     * 将一个向量围绕X轴旋转angle个角度
     *
     * @param v     向量
     * @param angle 角度
     * @return {@link Vector}
     */
    public static Vector rotateAroundAxisX(Vector v, double angle) {
        angle = Math.toRadians(angle);
        double y, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        y = v.getY() * cos - v.getZ() * sin;
        z = v.getY() * sin + v.getZ() * cos;
        return v.setY(y).setZ(z);
    }

    /**
     * 将一个向量围绕Y轴旋转angle个角度
     *
     * @param v     向量
     * @param angle 角度
     * @return {@link Vector}
     */
    public static Vector rotateAroundAxisY(Vector v, double angle) {
        angle = -angle;
        angle = Math.toRadians(angle);
        double x, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = v.getX() * cos + v.getZ() * sin;
        z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }

    /**
     * 将一个向量围绕Z轴旋转angle个角度
     *
     * @param v     向量
     * @param angle 角度
     * @return {@link Vector}
     */
    public static Vector rotateAroundAxisZ(Vector v, double angle) {
        angle = Math.toRadians(angle);
        double x, y, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = v.getX() * cos - v.getY() * sin;
        y = v.getX() * sin + v.getY() * cos;
        return v.setX(x).setY(y);
    }

    public static void createACircleWithVector(Location loc,Double radius,Particle particle,Long SleepTime) {

        // 我们直接在X轴正半轴上加一个单位, 用来制作我们的第一个向量
        Vector originalVector = getVector(loc, loc.clone().add(1, 0, 0));
        originalVector.multiply(radius); // 圆的半径长度
        for (int degree = 0; degree < 360; degree++) {
            // 我们将向量进行旋转
            Vector vector = vectorUtil.rotateAroundAxisY(originalVector, degree);
            loc.add(vector);
            loc.getWorld().spawnParticle(particle, loc, 0);
            loc.subtract(vector);

            try {
                Thread.sleep(SleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 取第一个坐标到第二个坐标的向量
     *
     * @param firstLocation  坐标1
     * @param secondLocation 坐标2
     * @return {@link Vector}
     */
    public static Vector getVector(Location firstLocation, Location secondLocation) {
        return secondLocation.clone().subtract(firstLocation).toVector();
    }

    public static List<double[]> generateLine(Location startLocation,Location endLocation, int resolution) {
        double startX = startLocation.getX();
        double startY = startLocation.getY();
        double startZ = startLocation.getZ();
        double endX = endLocation.getX();
        double endY = endLocation.getX();
        double endZ = endLocation.getX();



        double XStep = (endX - startX) / resolution;
        // X 方向的「单元」
        double YStep = (endY - startY) / resolution;
        // Y 方向的「单元」
        double ZStep = (endZ - startZ) / resolution;
        // Z 方向的「单元」
        List<double[]> result = new ArrayList<>();
        for (int i = 0; i <= resolution; i++) {
            double[] point = new double[3];
            // {x, y, z} 这样的数组
            point[0] = startX;
            point[1] = startY;
            point[2] = startZ;
            result.add(point);
            // 加入点阵
            startX += XStep;
            startY += YStep;
            startZ += ZStep;
            // 移动到下一个单元

        }
        return result;
    }

    public static List<double[]> generateLine(double startX, double startY ,double startZ, double endX, double endY,double endZ, int resolution,Entity entity,float Yaw) {
        double XStep = (endX - startX) / (double) resolution;
        // X 方向的「单元」
        double YStep = (endY - startY) / (double) resolution;
        // Y 方向的「单元」
        double ZStep = (endZ - startZ) / (double) resolution;
        // Z 方向的「单元」
        List<double[]> result = new ArrayList<>();
        for (int i = 0; i <= resolution; i++) {
            double[] point = new double[3];
            // {x, y, z} 这样的数组
            point[0] = startX;
            point[1] = startY;
            point[2] = startZ;
            Location location = new Location(entity.getWorld(),startX,startY,startZ);
            location.setYaw(Yaw);
            Vector vector = rotateVector(location.clone().toVector(),Yaw,0);
            location.add(vector.normalize());
            entity.teleport(location);
            result.add(point);
            // 加入点阵
            startX += XStep;
            startY += YStep;
            startZ += ZStep;
            // 移动到下一个单元

        }
        return result;
    }



}
