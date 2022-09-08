package com.nana.mmoplugin.mmoplugin.util.Lock;

// 为线程安全，实现类变量的线程锁

import com.nana.mmoplugin.mmoplugin.util.Define.MmoUtil;

public class ClassLock implements MmoUtil {
    private CanLock l;

    public ClassLock(CanLock locked) {
        this.l = locked;
    }

    public void getLock() throws LockException {
        Long time = System.currentTimeMillis();
        while (true) {
            if (System.currentTimeMillis() - time > 1000 * 5) {
                throw new LockException("上锁等待时间超过5s");
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            if (l.getUser() == null) {
                l.setUser(this);
                continue;
            }
            // 验证是否加锁成功
            if (l.getUser() != this) {
                continue;
            } else {
                break;
            }
        }

    }

    public void release() {
        try {
            if (!l.getUser().equals(this)) {
            }
        } catch (NullPointerException e) {
            LockException ce = new LockException("在取得锁之前释放！");
            ce.printStackTrace();
            return;
        }

        l.setUser(null);

    }


    public class LockException extends Exception {
        private String message;

        public LockException(String message) {
            super(message);
            this.message = message;
        }
    }

}


