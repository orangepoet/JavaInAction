package cn.orangepoet.inaction.demo.serialize;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Closeable;
import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Application {
    public static void main(String[] args) {
        String filepath = "C:\\Users\\orange\\Desktop\\foo.txt";
        File objectFile = new File(filepath);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(objectFile))) {
            Foo foo = new Foo();
            foo.setName("foo1");
            foo.setDesc("desc1");
            foo.setNumber(11);
            oos.writeObject(foo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(objectFile))) {
            Foo foo = (Foo) ois.readObject();
            System.out.println(foo);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        String filepath2 = "C:\\Users\\orange\\Desktop\\foo2.txt";
        File objectFile2 = new File(filepath2);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(objectFile2))) {
            Foo2 foo = new Foo2();
            foo.setName("foo2");
            foo.setDesc("desc2");
            foo.setNumber(15);
            oos.writeObject(foo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(objectFile2))) {
            Foo2 foo = (Foo2) ois.readObject();
            System.out.println(foo);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static class Orange implements Closeable {

        @Override
        public void close() throws IOException {
            System.out.println("closing");
        }
    }

    private void testTryFinally() throws IOException {
        try (Orange orange = new Orange()) {
            orange.close();
        }
    }

    @Getter
    @Setter
    @ToString
    public static class Foo implements Serializable {
        private static final long serialVersionUID = -4484245326713440912L;

        private String name;
        private String desc;
        private transient int number;
    }

    @Getter
    @Setter
    @ToString
    public static class Foo2 implements Externalizable {
        private static final long serialVersionUID = 8592191168309613341L;

        private String name;
        private String desc;
        private int number;

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeObject(this.name);
            out.writeObject(this.desc);
            out.writeObject(this.number);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            this.name = (String) in.readObject();
            this.desc = (String) in.readObject();
            this.number = (int) in.readObject();
        }
    }
}
