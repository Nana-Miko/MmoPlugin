package com.nana.mmoplugin.mmoplugin.util;

// 为线程安全，实现类变量的线程锁

public class ClassLock {
    private CanLock l;
    public ClassLock(CanLock locked) {
        this.l = locked;
    }

    public void getLock(){
        while(true){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            if (l.getUser() == null){
                l.setUser(this);continue;}
            // 验证是否加锁成功
            if (l.getUser() != this){continue;}
            else{break;}
        }

    }

    public void release(){
        if (!l.getUser().equals(this)){
            try {
                throw new LockException("在取得锁之前释放！");
            } catch (LockException e) {
                e.printStackTrace();
                return;
            }
        }

        l.setUser(null);

    }



    class LockException extends Exception{
        private String message;

        public LockException(String message){
            super(message);
            this.message = message;
        }
    }

}


