package com.alex.defs.objects;

import com.alex.io.InputStream;
import com.alex.io.OutputStream;
import com.alex.filestore.Store;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectDefinitions implements Cloneable {
    private static final ConcurrentHashMap objectDefinitions = new ConcurrentHashMap();
    private short[] originalColors;
    int[] toObjectIds;
    public int[] childrenIds;
    static int anInt3832;
    int[] anIntArray3833 = null;
    public int anInt3834;
    int anInt3835;

    static int anInt3836;
    public byte aByte3837;
    int anInt3838 = -1;
    public HashMap<Integer, Object> clientScriptData;
    boolean aBoolean3839;
    public int anInt3840;
    public int anInt3841;
    static int anInt3842;
    static int anInt3843;
    int anInt3844;
    boolean aBoolean3845;
    public int models[];
    public int arrayForModelId[];
    public byte aByte3847;
    public boolean ignoreClipOnAlternativeRoute;
    int[] animations = null;
    private byte[] possibleTypes;
    private int[] anIntArray4534;
    private byte[] unknownArray4;
    private byte[] unknownArray3;
    private int cflag;
    public byte aByte3849;
    int anInt3850;
    int anInt3851;
    public boolean secondBool;
    public boolean aBoolean3853;
    int anInt3855;
    public boolean notCliped;
    int anInt3857;
    private byte[] aByteArray3858;
    int[] anIntArray3859;
    int anInt3860;
    public String[] options;
    int configFileId;
    private short[] modifiedColors;
    int anInt3865;
    boolean aBoolean3866;
    boolean aBoolean3867;
    private int[] anIntArray3869;
    boolean aBoolean3870;
    public int sizeY;
    boolean aBoolean3872;
    boolean aBoolean3873;
    public int thirdInt;
    public int anInt3875;
    public int objectAnimation;
    public int anInt3877;
    public int anInt3878;
    public int clipType;
    public int anInt3881;
    public int anInt3882;
    public int anInt3883;
    Object loader;
    public int anInt3889;
    public int sizeX;
    public boolean aBoolean3891;
    int anInt3892;
    public int secondInt;
    boolean aBoolean3894;
    boolean aBoolean3895;
    int anInt3896;
    int configId;
    private byte[] aByteArray3899;
    int anInt3900;
    public String name;
    public int anInt3902;
    int anInt3904;
    int anInt3905;
    boolean aBoolean3906;
    int[] anIntArray3908;
    public byte aByte3912;
    int anInt3913;
    public byte aByte3914;
    public int anInt3915;
    public int[][] modelIds;
    public int anInt3917;
    public boolean loaded;
    private short[] aShortArray3919;
    private short[] aShortArray3920;
    int anInt3921;
    private HashMap parameters;
    boolean aBoolean3923;
    boolean aBoolean3924;
    int anInt3925;
    public int id;

    @Override
    public String toString() {
        return this.name != null ? this.name : "" + this.id;
    }

    public short[] modifiedModelColors;

    public boolean projectileCliped;


    public int anInt3876;


    private int[][] anIntArrayArray3916;

    public short[] modifiedTextureColors;
    public short[] originalTextureColors;

    private Object aClass194_3922;


    public void setOriginalColors(short[] originalColors) {
        this.originalColors = originalColors;
    }

    public void setModifiedColors(short[] modifiedColors) {
        this.modifiedColors = modifiedColors;
    }

    public short[] getOriginalColors() {
        return originalColors;
    }

    public short[] getModifiedColors() {
        return modifiedColors;
    }

    public ObjectDefinitions() {
        this.name = "null";
        this.anInt3835 = -1;
        this.anInt3860 = -1;
        this.configFileId = -1;
        this.aBoolean3866 = false;
        this.anInt3851 = -1;
        this.anInt3865 = 255;
        this.aBoolean3845 = false;
        this.aBoolean3867 = false;
        this.anInt3850 = 0;
        this.anInt3844 = -1;
        this.anInt3881 = 0;
        this.anInt3857 = -1;
        this.aBoolean3872 = true;
        this.anInt3882 = -1;
        this.anInt3834 = 0;
        this.options = new String[5];
        this.anInt3875 = 0;
        this.aBoolean3839 = false;
        this.anIntArray3869 = null;
        this.sizeY = 1;
        this.thirdInt = -1;
        this.anInt3883 = 0;
        this.aBoolean3895 = true;
        this.anInt3840 = 0;
        this.aBoolean3870 = false;
        this.anInt3889 = 0;
        this.aBoolean3853 = true;
        this.secondBool = false;
        this.clipType = 2;
        this.projectileCliped = true;
        this.ignoreClipOnAlternativeRoute = false;
        this.anInt3855 = -1;
        this.anInt3878 = 0;
        this.anInt3904 = 0;
        this.sizeX = 1;
        this.objectAnimation = -1;
        this.aBoolean3891 = false;
        this.anInt3905 = 0;
        this.anInt3913 = -1;
        this.aBoolean3906 = false;
        this.aBoolean3873 = false;
        this.aByte3914 = 0;
        this.anInt3915 = 0;
        this.anInt3900 = 0;
        this.secondInt = -1;
        this.aBoolean3894 = false;
        this.aByte3912 = 0;
        this.anInt3921 = 0;
        this.anInt3902 = 128;
        this.configId = -1;
        this.anInt3877 = 0;
        this.anInt3925 = 0;
        this.anInt3892 = 64;
        this.aBoolean3923 = false;
        this.aBoolean3924 = false;
        this.anInt3841 = 128;
        this.anInt3917 = 128;
    }

    public String getFirstOption() {
        return this.options != null && this.options.length >= 1 ? this.options[0] : "";
    }

    public String getSecondOption() {
        return this.options != null && this.options.length >= 2 ? this.options[1] : "";
    }


    public int getSizeX() {
        return sizeX;
    }


    public int getSizeY() {
        return sizeY;
    }

    public int getObjectAnimation() {
        return objectAnimation;
    }

    public int[][] getModelIds() {
        return modelIds;
    }

    public String[] getOpts() {
        return options;
    }

    public short[] getOriginalModelColors() {
        return originalColors;
    }

    public short[] getModifiedModelColors() {
        return modifiedColors;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public void setObjectAnimation(int objectAnimation) {
        this.objectAnimation = objectAnimation;
    }


    public void setModelIds(int[][] modelIds) {
        this.modelIds = modelIds;
    }


    public void invOptions(String[] invOptions) {
        this.options = invOptions;
    }


    public void setModelColors(short[] originalModelColors, short[] modifiedModelColors) {
        this.originalColors = originalModelColors;
        this.modifiedColors = modifiedModelColors;
    }

    public String getOption(int option) {
        return this.options != null && this.options.length >= option && option != 0 ? this.options[option - 1] : "";
    }

    public String getThirdOption() {
        return this.options != null && this.options.length >= 3 ? this.options[2] : "";
    }

    private static int getArchiveId(int i_0_) {
        return i_0_ >>> -1135990488;
    }

    private Object getValue(Field field) throws Throwable {
        field.setAccessible(true);
        Class type = field.getType();
        return type == int[][].class ? Arrays.toString((int[][]) field.get(this)) : (type == int[].class ? Arrays.toString((int[]) field.get(this)) : (type == byte[].class ? Arrays.toString((byte[]) field.get(this)) : (type == short[].class ? Arrays.toString((short[]) field.get(this)) : (type == double[].class ? Arrays.toString((double[]) field.get(this)) : (type == float[].class ? Arrays.toString((float[]) field.get(this)) : (type == Object[].class ? Arrays.toString((Object[]) field.get(this)) : field.get(this)))))));
    }

    public boolean isProjectileCliped() {
        return this.projectileCliped;
    }

    public boolean containsOption(int i, String option) {
        return this.options != null && this.options[i] != null && this.options.length > i ? this.options[i].equals(option) : false;
    }

    public boolean containsOption(String o) {
        if (this.options == null) {
            return false;
        } else {
            String[] var5 = this.options;
            int var4 = this.options.length;

            for (int var3 = 0; var3 < var4; ++var3) {
                String option = var5[var3];
                if (option != null && option.equalsIgnoreCase(o)) {
                    return true;
                }
            }

            return false;
        }
    }

    final void method3287() {
        if (this.secondInt == -1) {
            this.secondInt = 0;
            if (this.possibleTypes != null && this.possibleTypes.length == 1 && this.possibleTypes[0] == 10) {
                this.secondInt = 1;
            }

            for (int i_13_ = 0; i_13_ < 5; ++i_13_) {
                if (this.options[i_13_] != null) {
                    this.secondInt = 1;
                    break;
                }
            }
        }

        if (this.anInt3855 == -1) {
            this.anInt3855 = this.clipType != 0 ? 1 : 0;
        }

    }

    public int getAccessBlockFlag() {
        return this.cflag;
    }


    private void readValues(InputStream stream, int opcode) {
        if (opcode == 1 || opcode == 5) {
            int length = stream.readUnsignedByte();
            models = new int[length];
            if (opcode == 1) {
                arrayForModelId = new int[length];
            }
            for (int index = 0; index < length; index++) {
                models[index] = stream.readUnsignedShort();
                if (opcode == 1) {
                    arrayForModelId[index] = stream.readUnsignedByte();
                }
            }
        }
        //if (opcode == 2)
        //    name = stream.readString();
        if (opcode != 2) {
            if (opcode != 14) {
                if (opcode != 15) {
                    if (opcode == 17) {
                        projectileCliped = false;
                        clipType = 0;
                    } else if (opcode != 18) {
                        if (opcode == 19)
                            secondInt = stream.readUnsignedByte();
                        else if (opcode == 21)
                            aByte3912 = (byte) 1;
                        else if (opcode != 22) {
                            if (opcode != 23) {
                                if (opcode != 24) {
                                    if (opcode == 27)
                                        clipType = 1;
                                    else if (opcode == 28)
                                        anInt3892 = (stream
                                                .readUnsignedByte() << 2);
                                    else if (opcode != 29) {
                                        if (opcode != 39) {
                                            if (opcode < 30 || opcode >= 35) {
                                                if (opcode == 40) {
                                                    int i_53_ = (stream
                                                            .readUnsignedByte());
                                                    originalColors = new short[i_53_];
                                                    modifiedColors = new short[i_53_];
                                                    for (int i_54_ = 0; i_53_ > i_54_; i_54_++) {
                                                        originalColors[i_54_] = (short) (stream
                                                                .readUnsignedShort());
                                                        modifiedColors[i_54_] = (short) (stream
                                                                .readUnsignedShort());
                                                    }
                                                } else if (opcode != 41) {
                                                    if (opcode != 42) {
                                                        if (opcode != 62) {
                                                            if (opcode != 64) {
                                                                if (opcode == 65)
                                                                    anInt3902 = stream
                                                                            .readUnsignedShort();
                                                                else if (opcode != 66) {
                                                                    if (opcode != 67) {
                                                                        if (opcode == 69)
                                                                            anInt3925 = stream
                                                                                    .readUnsignedByte();
                                                                        else if (opcode != 70) {
                                                                            if (opcode == 71)
                                                                                anInt3889 = stream
                                                                                        .readShort() << 2;
                                                                            else if (opcode != 72) {
                                                                                if (opcode == 73)
                                                                                    secondBool = true;
                                                                                else if (opcode == 74)
                                                                                    notCliped = true;
                                                                                else if (opcode != 75) {
                                                                                    if (opcode != 77
                                                                                            && opcode != 92) {
                                                                                        if (opcode == 78) {
                                                                                            anInt3860 = stream
                                                                                                    .readUnsignedShort();
                                                                                            anInt3904 = stream
                                                                                                    .readUnsignedByte();
                                                                                        } else if (opcode != 79) {
                                                                                            if (opcode == 81) {
                                                                                                aByte3912 = (byte) 2;
                                                                                                anInt3882 = 256 * stream
                                                                                                        .readUnsignedByte();
                                                                                            } else if (opcode != 82) {
                                                                                                if (opcode == 88)
                                                                                                    aBoolean3853 = false;
                                                                                                else if (opcode != 89) {
                                                                                                    if (opcode == 90)
                                                                                                        aBoolean3870 = true;
                                                                                                    else if (opcode != 91) {
                                                                                                        if (opcode != 93) {
                                                                                                            if (opcode == 94)
                                                                                                                aByte3912 = (byte) 4;
                                                                                                            else if (opcode != 95) {
                                                                                                                if (opcode != 96) {
                                                                                                                    if (opcode == 97)
                                                                                                                        aBoolean3866 = true;
                                                                                                                    else if (opcode == 98)
                                                                                                                        aBoolean3923 = true;
                                                                                                                    else if (opcode == 99) {
                                                                                                                        anInt3857 = stream
                                                                                                                                .readUnsignedByte();
                                                                                                                        anInt3835 = stream
                                                                                                                                .readUnsignedShort();
                                                                                                                    } else if (opcode == 100) {
                                                                                                                        anInt3844 = stream
                                                                                                                                .readUnsignedByte();
                                                                                                                        anInt3913 = stream
                                                                                                                                .readUnsignedShort();
                                                                                                                    } else if (opcode != 101) {
                                                                                                                        if (opcode == 102)
                                                                                                                            anInt3838 = stream
                                                                                                                                    .readUnsignedShort();
                                                                                                                        else if (opcode == 103)
                                                                                                                            thirdInt = 0;
                                                                                                                        else if (opcode != 104) {
                                                                                                                            if (opcode == 105)
                                                                                                                                aBoolean3906 = true;
                                                                                                                            else if (opcode == 106) {
                                                                                                                                int i_55_ = stream
                                                                                                                                        .readUnsignedByte();
                                                                                                                                anIntArray3869 = new int[i_55_];
                                                                                                                                anIntArray3833 = new int[i_55_];
                                                                                                                                for (int i_56_ = 0; i_56_ < i_55_; i_56_++) {
                                                                                                                                    anIntArray3833[i_56_] = stream
                                                                                                                                            .readUnsignedShort();
                                                                                                                                    int i_57_ = stream
                                                                                                                                            .readUnsignedByte();
                                                                                                                                    anIntArray3869[i_56_] = i_57_;
                                                                                                                                    anInt3881 += i_57_;
                                                                                                                                }
                                                                                                                            } else if (opcode == 107)
                                                                                                                                anInt3851 = stream
                                                                                                                                        .readUnsignedShort();
                                                                                                                            else if (opcode >= 150 && opcode < 155) {
                                                                                                                                options[opcode + -150] = stream.readString();
																																	/*if (!loader.showOptions)
																																		options[opcode	+ -150] = null;*/
                                                                                                                            } else if (opcode != 160) {
                                                                                                                                if (opcode == 162) {
                                                                                                                                    aByte3912 = (byte) 3;
                                                                                                                                    anInt3882 = stream
                                                                                                                                            .readInt();
                                                                                                                                } else if (opcode == 163) {
                                                                                                                                    aByte3847 = (byte) stream
                                                                                                                                            .readByte();
                                                                                                                                    aByte3849 = (byte) stream
                                                                                                                                            .readByte();
                                                                                                                                    aByte3837 = (byte) stream
                                                                                                                                            .readByte();
                                                                                                                                    aByte3914 = (byte) stream
                                                                                                                                            .readByte();
                                                                                                                                } else if (opcode != 164) {
                                                                                                                                    if (opcode != 165) {
                                                                                                                                        if (opcode != 166) {
                                                                                                                                            if (opcode == 167)
                                                                                                                                                anInt3921 = stream
                                                                                                                                                        .readUnsignedShort();
                                                                                                                                            else if (opcode != 168) {
                                                                                                                                                if (opcode == 169) {
                                                                                                                                                    aBoolean3845 = true;
                                                                                                                                                    //added opcode
                                                                                                                                                } else if (opcode == 170) {
                                                                                                                                                    int anInt3383 = stream.readUnsignedSmart();
                                                                                                                                                    //added opcode
                                                                                                                                                } else if (opcode == 171) {
                                                                                                                                                    int anInt3362 = stream.readUnsignedSmart();
                                                                                                                                                    //added opcode
                                                                                                                                                } else if (opcode == 173) {
                                                                                                                                                    int anInt3302 = stream.readUnsignedShort();
                                                                                                                                                    int anInt3336 = stream.readUnsignedShort();
                                                                                                                                                    //added opcode
                                                                                                                                                } else if (opcode == 177) {
                                                                                                                                                    boolean ub = true;
                                                                                                                                                    //added opcode
                                                                                                                                                } else if (opcode == 178) {
                                                                                                                                                    int db = stream.readUnsignedByte();
                                                                                                                                                } else if (opcode == 249) {
                                                                                                                                                    int i_58_ = stream.readUnsignedByte();
                                                                                                                                                    if (clientScriptData == null)
                                                                                                                                                        clientScriptData = new HashMap<Integer, Object>(i_58_);
                                                                                                                                                    for (int i_60_ = 0; i_60_ < i_58_; i_60_++) {
                                                                                                                                                        boolean bool = stream.readUnsignedByte() == 1;
                                                                                                                                                        int key = stream.read24BitInt();
                                                                                                                                                        Object value;
                                                                                                                                                        if (bool)
                                                                                                                                                            value = stream.readString();
                                                                                                                                                        else
                                                                                                                                                            value = stream.readInt();
                                                                                                                                                        clientScriptData.put(key, value);
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            } else
                                                                                                                                                aBoolean3894 = true;
                                                                                                                                        } else
                                                                                                                                            anInt3877 = stream
                                                                                                                                                    .readShort();
                                                                                                                                    } else
                                                                                                                                        anInt3875 = stream
                                                                                                                                                .readShort();
                                                                                                                                } else
                                                                                                                                    anInt3834 = stream
                                                                                                                                            .readShort();
                                                                                                                            } else {
                                                                                                                                int i_62_ = stream
                                                                                                                                        .readUnsignedByte();
                                                                                                                                anIntArray3908 = new int[i_62_];
                                                                                                                                for (int i_63_ = 0; i_62_ > i_63_; i_63_++)
                                                                                                                                    anIntArray3908[i_63_] = stream
                                                                                                                                            .readUnsignedShort();
                                                                                                                            }
                                                                                                                        } else
                                                                                                                            anInt3865 = stream
                                                                                                                                    .readUnsignedByte();
                                                                                                                    } else
                                                                                                                        anInt3850 = stream
                                                                                                                                .readUnsignedByte();
                                                                                                                } else
                                                                                                                    aBoolean3924 = true;
                                                                                                            } else {
                                                                                                                aByte3912 = (byte) 5;
                                                                                                                anInt3882 = stream
                                                                                                                        .readShort();
                                                                                                            }
                                                                                                        } else {
                                                                                                            aByte3912 = (byte) 3;
                                                                                                            anInt3882 = stream
                                                                                                                    .readUnsignedShort();
                                                                                                        }
                                                                                                    } else
                                                                                                        aBoolean3873 = true;
                                                                                                } else
                                                                                                    aBoolean3895 = false;
                                                                                            } else
                                                                                                aBoolean3891 = true;
                                                                                        } else {
                                                                                            anInt3900 = stream
                                                                                                    .readUnsignedShort();
                                                                                            anInt3905 = stream
                                                                                                    .readUnsignedShort();
                                                                                            anInt3904 = stream
                                                                                                    .readUnsignedByte();
                                                                                            int i_64_ = stream
                                                                                                    .readUnsignedByte();
                                                                                            anIntArray3859 = new int[i_64_];
                                                                                            for (int i_65_ = 0; i_65_ < i_64_; i_65_++)
                                                                                                anIntArray3859[i_65_] = stream
                                                                                                        .readUnsignedShort();
                                                                                        }
                                                                                    } else {
                                                                                        configFileId = stream
                                                                                                .readUnsignedShort();
                                                                                        if (configFileId == 65535)
                                                                                            configFileId = -1;
                                                                                        configId = stream
                                                                                                .readUnsignedShort();
                                                                                        if (configId == 65535)
                                                                                            configId = -1;
                                                                                        int i_66_ = -1;
                                                                                        if (opcode == 92) {
                                                                                            i_66_ = stream
                                                                                                    .readUnsignedShort();
                                                                                            if (i_66_ == 65535)
                                                                                                i_66_ = -1;
                                                                                        }
                                                                                        int i_67_ = stream
                                                                                                .readUnsignedByte();
                                                                                        childrenIds = new int[i_67_
                                                                                                - -2];
                                                                                        for (int i_68_ = 0; i_67_ >= i_68_; i_68_++) {
                                                                                            childrenIds[i_68_] = stream
                                                                                                    .readUnsignedShort();
                                                                                            if (childrenIds[i_68_] == 65535)
                                                                                                childrenIds[i_68_] = -1;
                                                                                        }
                                                                                        childrenIds[i_67_ + 1] = i_66_;
                                                                                    }
                                                                                } else
                                                                                    anInt3855 = stream
                                                                                            .readUnsignedByte();
                                                                            } else
                                                                                anInt3915 = stream
                                                                                        .readShort() << 2;
                                                                        } else
                                                                            anInt3883 = stream
                                                                                    .readShort() << 2;
                                                                    } else
                                                                        anInt3917 = stream
                                                                                .readUnsignedShort();
                                                                } else
                                                                    anInt3841 = stream
                                                                            .readUnsignedShort();
                                                            } else
                                                                aBoolean3872 = false;
                                                        } else
                                                            aBoolean3839 = true;
                                                    } else {
                                                        int i_69_ = (stream
                                                                .readUnsignedByte());
                                                        aByteArray3858 = (new byte[i_69_]);
                                                        for (int i_70_ = 0; i_70_ < i_69_; i_70_++)
                                                            aByteArray3858[i_70_] = (byte) (stream
                                                                    .readByte());
                                                    }
                                                } else {
                                                    int i_71_ = (stream
                                                            .readUnsignedByte());
                                                    originalTextureColors = new short[i_71_];
                                                    modifiedTextureColors = new short[i_71_];
                                                    for (int i_72_ = 0; i_71_ > i_72_; i_72_++) {
                                                        originalTextureColors[i_72_] = (short) (stream
                                                                .readUnsignedShort());
                                                        modifiedTextureColors[i_72_] = (short) (stream
                                                                .readUnsignedShort());
                                                    }
                                                }
                                            } else
                                                options[-30
                                                        + opcode] = (stream
                                                        .readString());
                                        } else
                                            anInt3840 = (stream.readByte() * 5);
                                    } else
                                        anInt3878 = stream.readByte();
                                } else {
                                    anInt3876 = stream.readUnsignedShort();
                                    if (anInt3876 == 65535)
                                        anInt3876 = -1;
                                }
                            } else
                                thirdInt = 1;
                        } else
                            aBoolean3867 = true;
                    } else
                        projectileCliped = false;
                } else
                    sizeY = stream.readUnsignedByte();
            } else
                sizeX = stream.readUnsignedByte();
        } else {
            name = stream.readString();
        }
    }

    private void setDefaultsVariableValues() {
        anInt3835 = -1;
        anInt3860 = -1;
        configFileId = -1;
        aBoolean3866 = false;
        anInt3851 = -1;
        anInt3865 = 255;
        aBoolean3845 = false;
        aBoolean3867 = false;
        anInt3850 = 0;
        anInt3844 = -1;
        anInt3881 = 0;
        anInt3857 = -1;
        aBoolean3872 = true;
        anInt3882 = -1;
        anInt3834 = 0;
        anInt3875 = 0;
        aBoolean3839 = false;
        anIntArray3869 = null;
        sizeY = 1;
        thirdInt = -1;
        projectileCliped = true;
        anInt3883 = 0;
        aBoolean3895 = true;
        anInt3840 = 0;
        aBoolean3870 = false;
        anInt3889 = 0;
        aBoolean3853 = true;
        secondBool = false;
        clipType = 2;
        anInt3855 = -1;
        anInt3878 = 0;
        anInt3904 = 0;
        sizeX = 1;
        anInt3876 = -1;
        notCliped = false;
        aBoolean3891 = false;
        anInt3905 = 0;
        name = "null";
        anInt3913 = -1;
        aBoolean3906 = false;
        aBoolean3873 = false;
        aByte3914 = (byte) 0;
        anInt3915 = 0;
        anInt3900 = 0;
        secondInt = -1;
        aBoolean3894 = false;
        aByte3912 = (byte) 0;
        anInt3921 = 0;
        anInt3902 = 128;
        configId = -1;
        anInt3877 = 0;
        anInt3925 = 0;
        anInt3892 = 64;
        aBoolean3923 = false;
        aBoolean3924 = false;
        anInt3841 = 128;
        anInt3917 = 128;
    }

    private void skipReadModelIds(InputStream stream) {
        int length = stream.readUnsignedByte();

        for (int index = 0; index < length; ++index) {
            stream.skip(1);
            int length2 = stream.readUnsignedByte();

            for (int i = 0; i < length2; ++i) {
                stream.readBigSmart();
            }
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

    public ObjectDefinitions(Store cache, int i) {
        this.anInt3835 = -1;
        this.anInt3860 = -1;
        this.configFileId = -1;
        this.aBoolean3866 = false;
        this.anInt3851 = -1;
        this.anInt3865 = 255;
        this.aBoolean3845 = false;
        this.aBoolean3867 = false;
        this.anInt3850 = 0;
        this.anInt3844 = -1;
        this.anInt3881 = 0;
        this.anInt3857 = -1;
        this.aBoolean3872 = true;
        this.anInt3882 = -1;
        this.anInt3834 = 0;
        this.options = new String[5];
        this.anInt3875 = 0;
        this.aBoolean3839 = false;
        this.anIntArray3869 = null;
        this.sizeY = 1;
        this.thirdInt = -1;
        this.anInt3883 = 0;
        this.aBoolean3895 = true;
        this.anInt3840 = 0;
        this.aBoolean3870 = false;
        this.anInt3889 = 0;
        this.aBoolean3853 = true;
        this.secondBool = false;
        this.clipType = 2;
        this.projectileCliped = true;
        this.notCliped = false;
        this.anInt3855 = -1;
        this.anInt3878 = 0;
        this.anInt3904 = 0;
        this.sizeX = 1;
        this.objectAnimation = -1;
        this.aBoolean3891 = false;
        this.anInt3905 = 0;
        this.name = "null";
        this.anInt3913 = -1;
        this.aBoolean3906 = false;
        this.aBoolean3873 = false;
        this.aByte3914 = 0;
        this.anInt3915 = 0;
        this.anInt3900 = 0;
        this.secondInt = -1;
        this.aBoolean3894 = false;
        this.aByte3912 = 0;
        this.anInt3921 = 0;
        this.anInt3902 = 128;
        this.configId = -1;
        this.anInt3877 = 0;
        this.anInt3925 = 0;
        this.anInt3892 = 64;
        this.aBoolean3923 = false;
        this.aBoolean3924 = false;
        this.anInt3841 = 128;
        this.anInt3917 = 128;
    }

    public int getArchiveId() {
        return this.id >>> -1135990488;
    }

    public int getFileId() {
        return 0xff & this.id;
    }

    public static ObjectDefinitions getObjectDefinitions(Store store, int id) {
        ObjectDefinitions def = (ObjectDefinitions) objectDefinitions.get(Integer.valueOf(id));
        if (def == null) {
            def = new ObjectDefinitions();
            def.id = id;
            byte[] data = store.getIndexes()[16].getFile(getArchiveId(id), id & 0xff);
            if (data != null) {
                def.readValueLoop(new InputStream(data));
            }

            def.method3287();
            objectDefinitions.put(Integer.valueOf(id), def);
        }

        return def;
    }

    private void loadObjectDefinition(Store store) {
        byte[] data = store.getIndexes()[16].getFile(id >>> 8, id & 0xFF);
        if (data == null) {
            System.out.println("FAILED LOADING OBJECT " + this.id);
        } else {
            try {
                this.readOpcodeValues(new InputStream(data));
            } catch (RuntimeException var4) {
                var4.printStackTrace();
            }

            this.loaded = true;
        }

    }

    private void readOpcodeValues(InputStream stream) {
        while (true) {
            int opcode = stream.readUnsignedByte();
            if (opcode == 0) {
                return;
            }

            this.readValues(stream, opcode);
        }
    }

    public static ObjectDefinitions getObjectDefinition(Store cache, int itemId) {
        return getObjectDefinition(cache, itemId, true);
    }

    public static ObjectDefinitions getObjectDefinition(Store cache, int itemId, boolean load) {
        return new ObjectDefinitions(cache, itemId, load);
    }

    public ObjectDefinitions(Store cache, int id, boolean load) {
        this.id = id;
        this.setDefaultVariableValues();
        this.setDefaultOptions();
        if (load) {
            this.loadObjectDefinition(cache);
        }

    }

    private void setDefaultOptions() {
        this.options = new String[5];
    }

    private void setDefaultVariableValues() {
        this.name = name;
        this.sizeX = 1;
        this.sizeY = 1;
        this.projectileCliped = true;
        this.clipType = 2;
        this.objectAnimation = -1;
    }

    public int getClipType() {
        return this.clipType;
    }

    public void setClipType(int clipType) {
        this.clipType = clipType;
    }

    public static void clearObjectDefinitions() {
        objectDefinitions.clear();
    }

    public void printFields() {
        Field[] arr$ = this.getClass().getDeclaredFields();
        int len$ = arr$.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            Field field = arr$[i$];
            if ((field.getModifiers() & 8) == 0) {
                try {
                    System.out.println(field.getName() + ": " + this.getValue(field));
                } catch (Throwable var6) {
                    var6.printStackTrace();
                }
            }
        }

        System.out.println("-- end of " + this.getClass().getSimpleName() + " fields --");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public byte[] encode() {
        OutputStream stream = new OutputStream();
        int modelsCount = this.models.length;
        for (int i = 0; i < modelsCount; ++i) {

            stream.writeShort(this.models[i]);

            if (this.arrayForModelId != null && this.arrayForModelId.length > i) {
                stream.writeByte(this.arrayForModelId[i]);
            }
        }
        if (this.name != null && !this.name.isEmpty() && !this.name.equals("null")) {
            stream.writeByte(2);
            stream.writeString(this.name);
        }

        if (this.sizeX != 1) {
            stream.writeByte(14);
            stream.write128Byte(this.sizeX);
        }
        if (this.sizeY != 1) {
            stream.writeByte(15);
            stream.writeByte(this.sizeY);
        }

        if (this.objectAnimation != -1) {
            stream.writeByte(24);
            stream.writeBigSmart(this.objectAnimation);
        }

        for (int i = 0; i < this.options.length; ++i) {
            if (this.options[i] != null && !this.options[i].equals("Hidden")) {
                stream.writeByte(30 + i);
                stream.writeString(this.options[i]);
            }
        }

        if (this.originalColors != null && this.modifiedColors != null) {
            stream.writeByte(40);
            stream.writeByte(this.originalColors.length);
            for (int i = 0; i < this.originalColors.length; ++i) {
                stream.writeShort(this.originalColors[i]);
                stream.writeShort(this.modifiedColors[i]);
            }
        }

        if (this.clipType == 0 && this.projectileCliped) {
            stream.writeByte(17);
        }
        if (this.projectileCliped) {
            stream.writeByte(18);
        }
        if (this.clipType == 1 || this.clipType == 2) {
            stream.writeByte(27);
        }

        if (this.clientScriptData != null) {
            stream.writeByte(249);
            stream.writeByte(this.clientScriptData.size());
            for (Map.Entry<Integer, Object> entry : this.clientScriptData.entrySet()) {
                int key = entry.getKey();
                Object value = entry.getValue();
                stream.writeByte((value instanceof String) ? 1 : 0);
                stream.write24BitInt(key);
                if (value instanceof String) {
                    stream.writeString((String) value);
                } else {
                    stream.writeInt((Integer) value);
                }
            }
        }

        stream.writeByte(0);

        byte[] encodedData = new byte[stream.getOffset()];
        stream.setOffset(0);
        stream.getBytes(encodedData, 0, encodedData.length);
        return encodedData;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public void write(Store store) {
        store.getIndexes()[16].putFile(this.getArchiveId(), this.getFileId(), this.encode());
    }

}
