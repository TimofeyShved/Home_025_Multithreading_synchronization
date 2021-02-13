package com.company;

public class Main {

    public static void main(String[] args) throws Exception {
        Resource resource = new Resource(); //
        resource.i=5;

        MyThread myThread = new MyThread(); // Создает Поток - one
        myThread.setName("one");
        MyThread myThread2 = new MyThread(); // Создает Поток - two
        myThread2.setName("two");

        myThread.setResource(resource); // запихиваем ему ссылку на ресурс
        myThread2.setResource(resource);

        myThread.start(); // запуск потока
        myThread2.start(); // запуск потока

        myThread.join(); // запускает выбранный поток до смерти
        myThread2.join(); // запускает выбранный поток до смерти

        System.out.println(resource.i);
    }
}

class MyThread extends Thread { // создать поток при помощи extends
    Resource resource;

    public void setResource(Resource resource){
        this.resource = resource;
    }

    @Override
    public void run(){
        resource.chengI(); // запускаем в ресурсе метод i++;
    }
}

class Resource{
    int i;

    public int getI(){
        return i;
    }

    public synchronized void setI(int i){ //можно синхронизировать целый метод
        this.i = i;
    }

    public void chengI(){
        System.out.println(this.i);
        synchronized (this){ // не даст доступ другим потокам, пока не выполнится, тот который начал работу
            int i = this.i;
            if (Thread.currentThread().getName().equals("one")){ // если поток one
                Thread.yield(); // дать возможность запустится другому потоку
            }
            i++;
            this.i=i;
        }
    }
}