package com.cache.defs;

import com.cache.store.Cache;
import com.cache.io.InputStream;
import com.cache.io.OutputStream;

import java.util.concurrent.ConcurrentHashMap;

public class AnimationDefinition implements Cloneable {
    private static final ConcurrentHashMap<Integer, AnimationDefinition> animationDefinitions = new ConcurrentHashMap<>();
    private static final int SEQUENCES_INDEX = 20;
    public static Cache cache;
    public static int id;
    public static int[] frames;
    public static int[] delays;
    public int loopCycles = 99;
    public int anInt2137;
    public int anInt2140 = -1;
    public boolean aBoolean2141 = false;
    public int priority = 5;
    public int leftHandEquip = -1;
    public int rightHandEquip = -1;
    public int anInt2145;
    public int[][] handledSounds;
    public boolean[] aBooleanArray2149;
    public int[] anIntArray2151;
    public boolean aBoolean2152 = false;
    public int anInt2155 = 2;
    public boolean aBoolean2158 = false;
    public boolean aBoolean2159 = false;
    public int anInt2162 = -1;
    public int loopDelay = -1;
    public int[] soundMinDelay;
    public int[] soundMaxDelay;
    public int[] anIntArray1362;
    public boolean effect2Sound;

    public static AnimationDefinition getAnimationDefinitions(int id, Cache cache) {
        AnimationDefinition def = animationDefinitions.get(Integer.valueOf(id));

        if (def == null) {
            def = new AnimationDefinition();

            byte[] data = cache.getIndexes()[SEQUENCES_INDEX].getFile(id >>> 7, id & 127);

            if (data != null) {
                def.readValueLoop(new InputStream(data));
            }
            def.method2394();

            animationDefinitions.put(Integer.valueOf(id), def);
        }

        return def;
    }

    private static void printAnimationDetails(int emoteId) {
        AnimationDefinition animation = getAnimationDefinitions(emoteId);
        if (animation != null) {
            // Print details for delays
            for (int i = 0; i < delays.length; i++) {
                System.out.println("delays[" + i + "] = " + delays[i]);
            }

            // Print details for frames
            for (int i = 0; i < frames.length; i++) {
                System.out.println("frames[" + i + "] = " + frames[i]);
            }

            // Print other properties
            System.out.println("loopDelay = " + animation.loopDelay);
            System.out.println("leftHandEquip = " + animation.leftHandEquip);
            System.out.println("priority = " + animation.priority);
            System.out.println("rightHandEquip = " + animation.rightHandEquip);
            System.out.println("loopCycles = " + animation.loopCycles);
            System.out.println("anInt2140 = " + animation.anInt2140);
            System.out.println("anInt2162 = " + animation.anInt2162);
            System.out.println("anInt2155 = " + animation.anInt2155);
            System.out.println("anInt2145 = " + animation.anInt2145);

            // Print array elements
            printArray(animation.anIntArray2151, "anIntArray2151");
            printArray(animation.aBooleanArray2149, "aBooleanArray2149");
            System.out.println("aBoolean2152 = " + animation.aBoolean2152);
            printArray(animation.anIntArray1362, "anIntArray1362");
        }
    }

    private static void printArray(int[] array, String arrayName) {
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                System.out.println(arrayName + "[" + i + "] = " + array[i]);
            }
        }
    }

    private static void printArray(boolean[] array, String arrayName) {
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                System.out.println(arrayName + "[" + i + "] = " + array[i]);
            }
        }
    }

    public static final AnimationDefinition getAnimationDefinitions(int emoteId) {
        try {
            AnimationDefinition definition = animationDefinitions.get(emoteId);
            if (definition == null) {
                byte[] data = cache.getIndexes()[SEQUENCES_INDEX].getFile(emoteId >>> 7, emoteId & 127);
                definition = new AnimationDefinition();
                if (data != null) {
                    definition.readValueLoop(new InputStream(data));
                }
                definition.method2394();
                animationDefinitions.put(emoteId, definition);
                id = emoteId;
            }
            return definition;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    private void readValueLoop(InputStream stream) {
        while (true) {
            int opcode = stream.readUnsignedByte();
            if (opcode == 0) {
                return;
            }

            this.readValues(stream, opcode);
        }
    }

    public int getEmoteTime() {
        if (delays == null) {
            return 0;
        } else {
            int ms = 0;
            int[] arr$ = delays;
            int len$ = arr$.length;

            for (int i$ = 0; i$ < len$; ++i$) {
                int i = arr$[i$];
                ms += i;
            }

            return ms * 30;
        }
    }

    public int getEmoteGameTickets() {
        return this.getEmoteTime() / 1000;
    }

    private void readValues(InputStream stream, int opcode) {
        int index, length;

        switch (opcode) {
            case 1:
                index = stream.readUnsignedShort();
                delays = new int[index];
                frames = new int[index];
                for (length = 0; length < index; length++) {
                    delays[length] = stream.readUnsignedShort();
                    frames[length] = stream.readUnsignedShort() + (stream.readUnsignedShort() << 16);
                }
                break;
            case 2:
                this.loopDelay = stream.readUnsignedShort();
                break;
            case 3:
                this.aBooleanArray2149 = new boolean[256];
                index = stream.readUnsignedByte();
                for (length = 0; length < index; length++) {
                    this.aBooleanArray2149[stream.readUnsignedByte()] = true;
                }
                break;
            case 4:
                this.aBoolean2152 = true;
                break;
            case 5:
                this.priority = stream.readUnsignedByte();
                break;
            case 6:
                this.rightHandEquip = stream.readUnsignedShort();
                break;
            case 7:
                this.leftHandEquip = stream.readUnsignedShort();
                break;
            case 8:
                this.loopCycles = stream.readUnsignedByte();
                break;
            case 9:
                this.anInt2140 = stream.readUnsignedByte();
                break;
            case 10:
                this.anInt2162 = stream.readUnsignedByte();
                break;
            case 11:
                this.anInt2155 = stream.readUnsignedByte();
                break;
            case 12:
                index = stream.readUnsignedByte();
                this.anIntArray2151 = new int[index];
                for (length = 0; length < index; length++) {
                    this.anIntArray2151[length] = stream.readUnsignedShort() + (stream.readUnsignedShort() << 16);
                }
                break;
            case 13:
                index = stream.readUnsignedShort();
                this.handledSounds = new int[index][];
                for (length = 0; length < index; length++) {
                    int counter = stream.readUnsignedByte();
                    if (counter > 0) {
                        this.handledSounds[length] = new int[counter];
                        this.handledSounds[length][0] = stream.read24BitInt();
                        for (int i = 1; i < counter; i++) {
                            this.handledSounds[length][i] = stream.readUnsignedShort();
                        }
                    }
                }
                break;
            case 14:
                this.aBoolean2141 = true;
                break;
            case 15:
                this.aBoolean2159 = true;
                break;
            case 16:
                this.aBoolean2158 = true;
                break;
            case 17:
                this.anInt2145 = stream.readUnsignedByte();
                break;
            case 18:
                this.effect2Sound = true;
                break;
            case 19:
                if (this.anIntArray1362 == null) {
                    this.anIntArray1362 = new int[handledSounds.length];
                    for (index = 0; index < handledSounds.length; index++) {
                        this.anIntArray1362[index] = 255;
                    }
                }
                anIntArray1362[stream.readUnsignedByte()] = stream.readUnsignedByte();
                break;
            case 20:
                if (this.soundMaxDelay == null || soundMinDelay == null) {
                    this.soundMaxDelay = new int[handledSounds.length];
                    this.soundMinDelay = new int[handledSounds.length];
                    for (index = 0; index < handledSounds.length; index++) {
                        this.soundMaxDelay[index] = 256;
                        this.soundMinDelay[index] = 256;
                    }
                }
                index = stream.readUnsignedByte();
                this.soundMaxDelay[index] = stream.readUnsignedShort();
                this.soundMinDelay[index] = stream.readUnsignedShort();
                break;
        }
    }

    public int getArchiveId() {
        return id >>> 134238215;
    }

    public int getFileId() {
        return 127 & id;
    }

    public byte[] encode() {
        OutputStream stream = new OutputStream();

        if (frames != null) {
            stream.writeByte(1);
            stream.writeShort(frames.length);
            for (int i = 0; i < frames.length; i++) {
                stream.writeShort(delays[i]);
                stream.writeInt(frames[i]);
            }
        }

        if (loopDelay != -1) {
            stream.writeByte(2);
            stream.writeShort(loopDelay);
        }

        if (aBooleanArray2149 != null) {
            stream.writeByte(3);
            stream.writeByte(aBooleanArray2149.length);
            for (int i = 0; i < aBooleanArray2149.length; i++) {
                if (aBooleanArray2149[i]) {
                    stream.writeByte(i);
                }
            }
        }

        if (aBoolean2152) {
            stream.writeByte(4);
        }

        if (priority != 5) {
            stream.writeByte(5);
            stream.writeByte(priority);
        }

        if (rightHandEquip != -1) {
            stream.writeByte(6);
            stream.writeShort(rightHandEquip);
        }

        if (leftHandEquip != -1) {
            stream.writeByte(7);
            stream.writeShort(leftHandEquip);
        }

        if (loopCycles != 99) {
            stream.writeByte(8);
            stream.writeByte(loopCycles);
        }

        if (anInt2140 != -1) {
            stream.writeByte(9);
            stream.writeByte(anInt2140);
        }

        if (anInt2162 != -1) {
            stream.writeByte(10);
            stream.writeByte(anInt2162);
        }

        if (anInt2155 != 2) {
            stream.writeByte(11);
            stream.writeByte(anInt2155);
        }

        if (anInt2145 != 0) {
            stream.writeByte(12);
            stream.writeByte(anInt2145);
        }

        if (anIntArray2151 != null) {
            stream.writeByte(13);
            stream.writeByte(anIntArray2151.length);
            for (int value : anIntArray2151) {
                stream.writeInt(value);
            }
        }

        if (handledSounds != null) {
            stream.writeByte(14);
            stream.writeByte(handledSounds.length);
            for (int[] sound : handledSounds) {
                stream.writeByte(sound.length);
                for (int s : sound) {
                    stream.writeShort(s);
                }
            }
        }

        if (aBoolean2141) {
            stream.writeByte(15);
        }

        if (aBoolean2159) {
            stream.writeByte(16);
        }

        if (aBoolean2158) {
            stream.writeByte(17);
        }

        if (anInt2145 != 0) {
            stream.writeByte(18);
        }

        if (soundMaxDelay != null && soundMinDelay != null) {
            stream.writeByte(20);
            for (int i = 0; i < soundMaxDelay.length; i++) {
                stream.writeShort(soundMaxDelay[i]);
                stream.writeShort(soundMinDelay[i]);
            }
        }

        stream.writeByte(0);
        byte[] var61 = new byte[stream.getOffset()];
        stream.setOffset(0);
        stream.getBytes(var61, 0, var61.length);
        return var61;
    }

    public void write(Cache cache) {
        cache.getIndexes()[SEQUENCES_INDEX].putFile(this.getArchiveId(), this.getFileId(), this.encode());
    }

    public void method2394() {
        if (anInt2140 == -1) {
            anInt2140 = aBooleanArray2149 == null ? 0 : 2;
        }
        if (anInt2162 == -1) {
            anInt2162 = aBooleanArray2149 == null ? 0 : 2;
        }
    }
}
