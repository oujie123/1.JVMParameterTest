package com.jvm.ex2;
/**
 *
 * 对象的自我拯救
 */
public class FinalizeGC {
    public static FinalizeGC instance = null;
    public void isAlive(){
        System.out.println("I am still alive!");
    }
    @Override
    protected void finalize() throws Throwable{
        super.finalize();
        System.out.println("finalize method executed");
        //第一次持有自己，不让回收
        FinalizeGC.instance = this;
    }

    /**
     * 实现了finalize的第一次GC在休眠等待一段时间之后会被存活，但是第二次不行，一定死
     * @param args
     * @throws Throwable
     */
    public static void main(String[] args) throws Throwable {
        instance = new FinalizeGC();
        //对象进行第1次GC
        instance =null;
        System.gc();
        Thread.sleep(1000);//Finalizer方法优先级很低，需要等待
        if(instance !=null){
            instance.isAlive();
        }else{
            System.out.println("I am dead！");
        }
        //对象进行第2次GC
        instance =null;
        System.gc();
        Thread.sleep(1000);
        if(instance !=null){
            instance.isAlive();
        }else{
            System.out.println("I am dead！");
        }
    }
}
