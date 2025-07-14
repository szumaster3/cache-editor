package com.misc.model.view.render;

import com.misc.model.view.Main;
import com.misc.model.view.properties.PanelProperties;

public final class ModelCanvas extends PanelProperties {
    public static final int[] field15 = new int[2048];
    public static int field11;
    public static int screenPosX;
    public static int screenPosY;
    public static int[] field16 = new int[2048];
    public static int[] field17 = new int[2048];
    public static int[] screen;
    /*private static int field20;
    private static int[][] field21;
    private static int[][] field22 = new int[50][];*/
    public static int[] faceLighting = new int[65536];
    static boolean field8;
    private static final boolean field7 = true;
    private static boolean field9;
    private static final boolean field10 = true;
    private static final int[] field14 = new int[512];
    private static final boolean[] field19 = new boolean[50];

    static {
        for (int i = 1; i < 512; ++i) {
            field14[i] = 32768 / i;
        }
        for (int i = 1; i < 2048; ++i) {
            field15[i] = 65536 / i;
        }
        for (int i = 0; i < 2048; ++i) {
            field16[i] = (int) (65536.0D * Math.sin((double) i * 0.0030679615D));
            field17[i] = (int) (65536.0D * Math.cos((double) i * 0.0030679615D));
        }

    }

   /*public static void method1() {
      if(field21 == null) {
         field20 = 20;
         if(field7) {
            field21 = new int[field20][16384];
         } else {
            field21 = new int[field20][65536];
         }
       }
	   for(int i = 0; i < 50; ++i) {
		   field22[i] = null;
	   }
   }*/

    public static void setDimensions(int width, int height) {
        screen = new int[height];
        for (int i = 0; i < height; ++i) {
            screen[i] = width * i;
        }
        screenPosX = width / 2;
        screenPosY = height / 2;
    }

    public static void setLighting(double brightness) {
        brightness += Math.random() * 0.03D - 0.015D;
        int faceIndex = 0;

        for (int i = 0; i < 512; i++) {
            double var4 = (double) (i / 8) / 64.0D + 0.0078125D;
            double var6 = (double) (i & 7) / 8.0D + 0.0625D;

            for (int var8 = 0; var8 < 128; ++var8) {
                double var9;
                double var11 = var9 = (double) var8 / 128.0D;
                double var13 = var9;
                double var15 = var9;
                if (var6 != 0.0D) {
                    double var17;
                    if (var9 < 0.5D) {
                        var17 = var9 * (var6 + 1.0D);
                    } else {
                        var17 = var9 + var6 - var9 * var6;
                    }

                    double var19 = var9 * 2.0D - var17;
                    double var21;
                    if ((var21 = var4 + 0.3333333333333333D) > 1.0D) {
                        --var21;
                    }

                    double var25;
                    if ((var25 = var4 - 0.3333333333333333D) < 0.0D) {
                        ++var25;
                    }

                    if (var21 * 6.0D < 1.0D) {
                        var11 = var19 + (var17 - var19) * 6.0D * var21;
                    } else if (var21 * 2.0D < 1.0D) {
                        var11 = var17;
                    } else if (var21 * 3.0D < 2.0D) {
                        var11 = var19 + (var17 - var19) * (0.6666666666666666D - var21) * 6.0D;
                    } else {
                        var11 = var19;
                    }

                    if (var4 * 6.0D < 1.0D) {
                        var13 = var19 + (var17 - var19) * 6.0D * var4;
                    } else if (var4 * 2.0D < 1.0D) {
                        var13 = var17;
                    } else if (var4 * 3.0D < 2.0D) {
                        var13 = var19 + (var17 - var19) * (0.6666666666666666D - var4) * 6.0D;
                    } else {
                        var13 = var19;
                    }

                    if (var25 * 6.0D < 1.0D) {
                        var15 = var19 + (var17 - var19) * 6.0D * var25;
                    } else if (var25 * 2.0D < 1.0D) {
                        var15 = var17;
                    } else if (var25 * 3.0D < 2.0D) {
                        var15 = var19 + (var17 - var19) * (0.6666666666666666D - var25) * 6.0D;
                    } else {
                        var15 = var19;
                    }
                }

                int var38 = (int) (var11 * 256.0D);
                int var18 = (int) (var13 * 256.0D);
                int var39 = (int) (var15 * 256.0D);
                int red;
                double var30 = (double) ((red = (var38 << 16) + (var18 << 8) + var39) >> 16) / 256.0D;
                double var32 = (double) (red >> 8 & 255) / 256.0D;
                double var34 = (double) (red & 255) / 256.0D;
                var30 = Math.pow(var30, brightness);
                var32 = Math.pow(var32, brightness);
                var34 = Math.pow(var34, brightness);
                red = (int) (var30 * 256.0D);
                int green = (int) (var32 * 256.0D);
                int blue = (int) (var34 * 256.0D);
                int rgb;
                if ((rgb = (red << 16) + (green << 8) + blue) == 0) {
                    rgb = 1;
                }

                faceLighting[faceIndex++] = rgb;
            }
        }

      /*for(int i = 0; i < 50; i++) {
         if(field22[i] != null) {
            field21[field20++] = field22[i];
            field22[i] = null;
         }
      }*/

    }

    public static void fillFace(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
        int var9 = 0;
        int var10 = 0;
        if (var1 != var0) {
            var9 = (var4 - var3 << 16) / (var1 - var0);
            var10 = (var7 - var6 << 15) / (var1 - var0);
        }

        int var11 = 0;
        int var12 = 0;
        if (var2 != var1) {
            var11 = (var5 - var4 << 16) / (var2 - var1);
            var12 = (var8 - var7 << 15) / (var2 - var1);
        }

        int var13 = 0;
        int var14 = 0;
        if (var2 != var0) {
            var13 = (var3 - var5 << 16) / (var0 - var2);
            var14 = (var6 - var8 << 15) / (var0 - var2);
        }

        if (var0 <= var1 && var0 <= var2) {
            if (var0 < PanelProperties.height2) {
                if (var1 > PanelProperties.height2) {
                    var1 = PanelProperties.height2;
                }

                if (var2 > PanelProperties.height2) {
                    var2 = PanelProperties.height2;
                }

                if (var1 < var2) {
                    var5 = var3 <<= 16;
                    var8 = var6 <<= 15;
                    if (var0 < 0) {
                        var5 = var3 - var13 * var0;
                        var3 -= var9 * var0;
                        var8 = var6 - var14 * var0;
                        var6 -= var10 * var0;
                        var0 = 0;
                    }

                    var4 <<= 16;
                    var7 <<= 15;
                    if (var1 < 0) {
                        var4 -= var11 * var1;
                        var7 -= var12 * var1;
                        var1 = 0;
                    }

                    if (var0 != var1 && var13 < var9 || var0 == var1 && var13 > var11) {
                        var2 -= var1;
                        var1 -= var0;
                        var0 = screen[var0];

                        while (true) {
                            --var1;
                            if (var1 < 0) {
                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        return;
                                    }

                                    method4(PanelProperties.pixels, var0, var5 >> 16, var4 >> 16, var8 >> 7, var7 >> 7);
                                    var5 += var13;
                                    var4 += var11;
                                    var8 += var14;
                                    var7 += var12;
                                    var0 += PanelProperties.width;
                                }
                            }

                            method4(PanelProperties.pixels, var0, var5 >> 16, var3 >> 16, var8 >> 7, var6 >> 7);
                            var5 += var13;
                            var3 += var9;
                            var8 += var14;
                            var6 += var10;
                            var0 += PanelProperties.width;
                        }
                    } else {
                        var2 -= var1;
                        var1 -= var0;
                        var0 = screen[var0];

                        while (true) {
                            --var1;
                            if (var1 < 0) {
                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        return;
                                    }

                                    method4(PanelProperties.pixels, var0, var4 >> 16, var5 >> 16, var7 >> 7, var8 >> 7);
                                    var5 += var13;
                                    var4 += var11;
                                    var8 += var14;
                                    var7 += var12;
                                    var0 += PanelProperties.width;
                                }
                            }

                            method4(PanelProperties.pixels, var0, var3 >> 16, var5 >> 16, var6 >> 7, var8 >> 7);
                            var5 += var13;
                            var3 += var9;
                            var8 += var14;
                            var6 += var10;
                            var0 += PanelProperties.width;
                        }
                    }
                } else {
                    var4 = var3 <<= 16;
                    var7 = var6 <<= 15;
                    if (var0 < 0) {
                        var4 = var3 - var13 * var0;
                        var3 -= var9 * var0;
                        var7 = var6 - var14 * var0;
                        var6 -= var10 * var0;
                        var0 = 0;
                    }

                    var5 <<= 16;
                    var8 <<= 15;
                    if (var2 < 0) {
                        var5 -= var11 * var2;
                        var8 -= var12 * var2;
                        var2 = 0;
                    }

                    if (var0 != var2 && var13 < var9 || var0 == var2 && var11 > var9) {
                        var1 -= var2;
                        var2 -= var0;
                        var0 = screen[var0];

                        while (true) {
                            --var2;
                            if (var2 < 0) {
                                while (true) {
                                    --var1;
                                    if (var1 < 0) {
                                        return;
                                    }

                                    method4(PanelProperties.pixels, var0, var5 >> 16, var3 >> 16, var8 >> 7, var6 >> 7);
                                    var5 += var11;
                                    var3 += var9;
                                    var8 += var12;
                                    var6 += var10;
                                    var0 += PanelProperties.width;
                                }
                            }

                            method4(PanelProperties.pixels, var0, var4 >> 16, var3 >> 16, var7 >> 7, var6 >> 7);
                            var4 += var13;
                            var3 += var9;
                            var7 += var14;
                            var6 += var10;
                            var0 += PanelProperties.width;
                        }
                    } else {
                        var1 -= var2;
                        var2 -= var0;
                        var0 = screen[var0];

                        while (true) {
                            --var2;
                            if (var2 < 0) {
                                while (true) {
                                    --var1;
                                    if (var1 < 0) {
                                        return;
                                    }

                                    method4(PanelProperties.pixels, var0, var3 >> 16, var5 >> 16, var6 >> 7, var8 >> 7);
                                    var5 += var11;
                                    var3 += var9;
                                    var8 += var12;
                                    var6 += var10;
                                    var0 += PanelProperties.width;
                                }
                            }

                            method4(PanelProperties.pixels, var0, var3 >> 16, var4 >> 16, var6 >> 7, var7 >> 7);
                            var4 += var13;
                            var3 += var9;
                            var7 += var14;
                            var6 += var10;
                            var0 += PanelProperties.width;
                        }
                    }
                }
            }
        } else if (var1 <= var2) {
            if (var1 < PanelProperties.height2) {
                if (var2 > PanelProperties.height2) {
                    var2 = PanelProperties.height2;
                }

                if (var0 > PanelProperties.height2) {
                    var0 = PanelProperties.height2;
                }

                if (var2 < var0) {
                    var3 = var4 <<= 16;
                    var6 = var7 <<= 15;
                    if (var1 < 0) {
                        var3 -= var9 * var1;
                        var4 -= var11 * var1;
                        var6 -= var10 * var1;
                        var7 -= var12 * var1;
                        var1 = 0;
                    }

                    var5 <<= 16;
                    var8 <<= 15;
                    if (var2 < 0) {
                        var5 -= var13 * var2;
                        var8 -= var14 * var2;
                        var2 = 0;
                    }

                    if (var1 != var2 && var9 < var11 || var1 == var2 && var9 > var13) {
                        var0 -= var2;
                        var2 -= var1;
                        var1 = screen[var1];

                        while (true) {
                            --var2;
                            if (var2 < 0) {
                                while (true) {
                                    --var0;
                                    if (var0 < 0) {
                                        return;
                                    }

                                    method4(PanelProperties.pixels, var1, var3 >> 16, var5 >> 16, var6 >> 7, var8 >> 7);
                                    var3 += var9;
                                    var5 += var13;
                                    var6 += var10;
                                    var8 += var14;
                                    var1 += PanelProperties.width;
                                }
                            }

                            method4(PanelProperties.pixels, var1, var3 >> 16, var4 >> 16, var6 >> 7, var7 >> 7);
                            var3 += var9;
                            var4 += var11;
                            var6 += var10;
                            var7 += var12;
                            var1 += PanelProperties.width;
                        }
                    } else {
                        var0 -= var2;
                        var2 -= var1;
                        var1 = screen[var1];

                        while (true) {
                            --var2;
                            if (var2 < 0) {
                                while (true) {
                                    --var0;
                                    if (var0 < 0) {
                                        return;
                                    }

                                    method4(PanelProperties.pixels, var1, var5 >> 16, var3 >> 16, var8 >> 7, var6 >> 7);
                                    var3 += var9;
                                    var5 += var13;
                                    var6 += var10;
                                    var8 += var14;
                                    var1 += PanelProperties.width;
                                }
                            }

                            method4(PanelProperties.pixels, var1, var4 >> 16, var3 >> 16, var7 >> 7, var6 >> 7);
                            var3 += var9;
                            var4 += var11;
                            var6 += var10;
                            var7 += var12;
                            var1 += PanelProperties.width;
                        }
                    }
                } else {
                    var5 = var4 <<= 16;
                    var8 = var7 <<= 15;
                    if (var1 < 0) {
                        var5 = var4 - var9 * var1;
                        var4 -= var11 * var1;
                        var8 = var7 - var10 * var1;
                        var7 -= var12 * var1;
                        var1 = 0;
                    }

                    var3 <<= 16;
                    var6 <<= 15;
                    if (var0 < 0) {
                        var3 -= var13 * var0;
                        var6 -= var14 * var0;
                        var0 = 0;
                    }

                    if (var9 < var11) {
                        var2 -= var0;
                        var0 -= var1;
                        var1 = screen[var1];

                        while (true) {
                            --var0;
                            if (var0 < 0) {
                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        return;
                                    }

                                    method4(PanelProperties.pixels, var1, var3 >> 16, var4 >> 16, var6 >> 7, var7 >> 7);
                                    var3 += var13;
                                    var4 += var11;
                                    var6 += var14;
                                    var7 += var12;
                                    var1 += PanelProperties.width;
                                }
                            }

                            method4(PanelProperties.pixels, var1, var5 >> 16, var4 >> 16, var8 >> 7, var7 >> 7);
                            var5 += var9;
                            var4 += var11;
                            var8 += var10;
                            var7 += var12;
                            var1 += PanelProperties.width;
                        }
                    } else {
                        var2 -= var0;
                        var0 -= var1;
                        var1 = screen[var1];

                        while (true) {
                            --var0;
                            if (var0 < 0) {
                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        return;
                                    }

                                    method4(PanelProperties.pixels, var1, var4 >> 16, var3 >> 16, var7 >> 7, var6 >> 7);
                                    var3 += var13;
                                    var4 += var11;
                                    var6 += var14;
                                    var7 += var12;
                                    var1 += PanelProperties.width;
                                }
                            }

                            method4(PanelProperties.pixels, var1, var4 >> 16, var5 >> 16, var7 >> 7, var8 >> 7);
                            var5 += var9;
                            var4 += var11;
                            var8 += var10;
                            var7 += var12;
                            var1 += PanelProperties.width;
                        }
                    }
                }
            }
        } else if (var2 < PanelProperties.height2) {
            if (var0 > PanelProperties.height2) {
                var0 = PanelProperties.height2;
            }

            if (var1 > PanelProperties.height2) {
                var1 = PanelProperties.height2;
            }

            if (var0 < var1) {
                var4 = var5 <<= 16;
                var7 = var8 <<= 15;
                if (var2 < 0) {
                    var4 -= var11 * var2;
                    var5 -= var13 * var2;
                    var7 -= var12 * var2;
                    var8 -= var14 * var2;
                    var2 = 0;
                }

                var3 <<= 16;
                var6 <<= 15;
                if (var0 < 0) {
                    var3 -= var9 * var0;
                    var6 -= var10 * var0;
                    var0 = 0;
                }

                if (var11 < var13) {
                    var1 -= var0;
                    var0 -= var2;
                    var2 = screen[var2];

                    while (true) {
                        --var0;
                        if (var0 < 0) {
                            while (true) {
                                --var1;
                                if (var1 < 0) {
                                    return;
                                }

                                method4(PanelProperties.pixels, var2, var4 >> 16, var3 >> 16, var7 >> 7, var6 >> 7);
                                var4 += var11;
                                var3 += var9;
                                var7 += var12;
                                var6 += var10;
                                var2 += PanelProperties.width;
                            }
                        }

                        method4(PanelProperties.pixels, var2, var4 >> 16, var5 >> 16, var7 >> 7, var8 >> 7);
                        var4 += var11;
                        var5 += var13;
                        var7 += var12;
                        var8 += var14;
                        var2 += PanelProperties.width;
                    }
                } else {
                    var1 -= var0;
                    var0 -= var2;
                    var2 = screen[var2];

                    while (true) {
                        --var0;
                        if (var0 < 0) {
                            while (true) {
                                --var1;
                                if (var1 < 0) {
                                    return;
                                }

                                method4(PanelProperties.pixels, var2, var3 >> 16, var4 >> 16, var6 >> 7, var7 >> 7);
                                var4 += var11;
                                var3 += var9;
                                var7 += var12;
                                var6 += var10;
                                var2 += PanelProperties.width;
                            }
                        }

                        method4(PanelProperties.pixels, var2, var5 >> 16, var4 >> 16, var8 >> 7, var7 >> 7);
                        var4 += var11;
                        var5 += var13;
                        var7 += var12;
                        var8 += var14;
                        var2 += PanelProperties.width;
                    }
                }
            } else {
                var3 = var5 <<= 16;
                var6 = var8 <<= 15;
                if (var2 < 0) {
                    var3 -= var11 * var2;
                    var5 -= var13 * var2;
                    var6 -= var12 * var2;
                    var8 -= var14 * var2;
                    var2 = 0;
                }

                var4 <<= 16;
                var7 <<= 15;
                if (var1 < 0) {
                    var4 -= var9 * var1;
                    var7 -= var10 * var1;
                    var1 = 0;
                }

                if (var11 < var13) {
                    var0 -= var1;
                    var1 -= var2;
                    var2 = screen[var2];

                    while (true) {
                        --var1;
                        if (var1 < 0) {
                            while (true) {
                                --var0;
                                if (var0 < 0) {
                                    return;
                                }

                                method4(PanelProperties.pixels, var2, var4 >> 16, var5 >> 16, var7 >> 7, var8 >> 7);
                                var4 += var9;
                                var5 += var13;
                                var7 += var10;
                                var8 += var14;
                                var2 += PanelProperties.width;
                            }
                        }

                        method4(PanelProperties.pixels, var2, var3 >> 16, var5 >> 16, var6 >> 7, var8 >> 7);
                        var3 += var11;
                        var5 += var13;
                        var6 += var12;
                        var8 += var14;
                        var2 += PanelProperties.width;
                    }
                } else {
                    var0 -= var1;
                    var1 -= var2;
                    var2 = screen[var2];

                    while (true) {
                        --var1;
                        if (var1 < 0) {
                            while (true) {
                                --var0;
                                if (var0 < 0) {
                                    return;
                                }

                                method4(PanelProperties.pixels, var2, var5 >> 16, var4 >> 16, var8 >> 7, var7 >> 7);
                                var4 += var9;
                                var5 += var13;
                                var7 += var10;
                                var8 += var14;
                                var2 += PanelProperties.width;
                            }
                        }

                        method4(PanelProperties.pixels, var2, var5 >> 16, var3 >> 16, var8 >> 7, var6 >> 7);
                        var3 += var11;
                        var5 += var13;
                        var6 += var12;
                        var8 += var14;
                        var2 += PanelProperties.width;
                    }
                }
            }
        }
    }

    private static void method4(int[] pixels, int var1, int var2, int var3, int var4, int var5) {
        int var6;
        int var7;
        int var8;
        int var9;
        if (field10) {
            if (field8) {
                if (var3 - var2 > 3) {
                    var5 = (var5 - var4) / (var3 - var2);
                } else {
                    var5 = 0;
                }

                if (var3 > PanelProperties.field65) {
                    var3 = PanelProperties.field65;
                }

                if (var2 < 0) {
                    var4 -= var2 * var5;
                    var2 = 0;
                }

                if (var2 >= var3) {
                    return;
                }

                var1 += var2;
                var6 = var3 - var2 >> 2;
                var5 <<= 2;
            } else {
                if (var2 >= var3) {
                    return;
                }

                var1 += var2;
                if ((var6 = var3 - var2 >> 2) > 0) {
                    var5 = (var5 - var4) * field14[var6] >> 15;
                } else {
                    var5 = 0;
                }
            }

            if (field11 != 0) {
                var7 = field11;
                var8 = 256 - field11;

                while (true) {
                    --var6;
                    if (var6 < 0) {
                        if ((var6 = var3 - var2 & 3) > 0) {
                            var9 = (((var9 = faceLighting[var4 >> 8]) & 16711935) * var8 >> 8 & 16711935) + ((var9 & '\uff00') * var8 >> 8 & '\uff00');
                            if (Main.isOptimizedSelected) {
                                do {
                                    pixels[var1] = var9 + ((pixels[var1] & 16711935) * var7 >> 8 & 16711935) + ((pixels[var1] & '\uff00') * var7 >> 8 & '\uff00');
                                    ++var1;
                                    --var6;
                                } while (var6 > 0);

                                return;
                            }

                            do {
                                pixels[var1++] = var9 + ((pixels[var1] & 16711935) * var7 >> 8 & 16711935) + ((pixels[var1] & '\uff00') * var7 >> 8 & '\uff00');
                                --var6;
                            } while (var6 > 0);
                        }
                        break;
                    }

                    var9 = faceLighting[var4 >> 8];
                    var4 += var5;
                    var9 = ((var9 & 16711935) * var8 >> 8 & 16711935) + ((var9 & '\uff00') * var8 >> 8 & '\uff00');
                    if (Main.isOptimizedSelected) {
                        pixels[var1] = var9 + ((pixels[var1] & 16711935) * var7 >> 8 & 16711935) + ((pixels[var1] & '\uff00') * var7 >> 8 & '\uff00');
                        ++var1;
                        pixels[var1] = var9 + ((pixels[var1] & 16711935) * var7 >> 8 & 16711935) + ((pixels[var1] & '\uff00') * var7 >> 8 & '\uff00');
                        ++var1;
                        pixels[var1] = var9 + ((pixels[var1] & 16711935) * var7 >> 8 & 16711935) + ((pixels[var1] & '\uff00') * var7 >> 8 & '\uff00');
                        ++var1;
                        pixels[var1] = var9 + ((pixels[var1] & 16711935) * var7 >> 8 & 16711935) + ((pixels[var1] & '\uff00') * var7 >> 8 & '\uff00');
                        ++var1;
                    } else {
                        pixels[var1++] = var9 + ((pixels[var1] & 16711935) * var7 >> 8 & 16711935) + ((pixels[var1] & '\uff00') * var7 >> 8 & '\uff00');
                        pixels[var1++] = var9 + ((pixels[var1] & 16711935) * var7 >> 8 & 16711935) + ((pixels[var1] & '\uff00') * var7 >> 8 & '\uff00');
                        pixels[var1++] = var9 + ((pixels[var1] & 16711935) * var7 >> 8 & 16711935) + ((pixels[var1] & '\uff00') * var7 >> 8 & '\uff00');
                        pixels[var1++] = var9 + ((pixels[var1] & 16711935) * var7 >> 8 & 16711935) + ((pixels[var1] & '\uff00') * var7 >> 8 & '\uff00');
                    }
                }
            } else {
                while (true) {
                    --var6;
                    if (var6 < 0) {
                        if ((var6 = var3 - var2 & 3) > 0) {
                            var7 = faceLighting[var4 >> 8];

                            do {
                                pixels[var1++] = var7;
                                --var6;
                            } while (var6 > 0);

                            return;
                        }
                        break;
                    }

                    var7 = faceLighting[var4 >> 8];
                    var4 += var5;
                    pixels[var1++] = var7;
                    pixels[var1++] = var7;
                    pixels[var1++] = var7;
                    pixels[var1++] = var7;
                }
            }

        } else if (var2 < var3) {
            var5 = (var5 - var4) / (var3 - var2);
            if (field8) {
                if (var3 > PanelProperties.field65) {
                    var3 = PanelProperties.field65;
                }

                if (var2 < 0) {
                    var4 -= var2 * var5;
                    var2 = 0;
                }

                if (var2 >= var3) {
                    return;
                }
            }

            var1 += var2;
            var6 = var3 - var2;
            if (field11 != 0) {
                var7 = field11;
                var8 = 256 - field11;

                do {
                    var9 = faceLighting[var4 >> 8];
                    var4 += var5;
                    var9 = ((var9 & 16711935) * var8 >> 8 & 16711935) + ((var9 & '\uff00') * var8 >> 8 & '\uff00');
                    if (Main.isOptimizedSelected) {
                        pixels[var1] = var9 + ((pixels[var1] & 16711935) * var7 >> 8 & 16711935) + ((pixels[var1] & '\uff00') * var7 >> 8 & '\uff00');
                        ++var1;
                    } else {
                        pixels[var1++] = var9 + ((pixels[var1] & 16711935) * var7 >> 8 & 16711935) + ((pixels[var1] & '\uff00') * var7 >> 8 & '\uff00');
                    }

                    --var6;
                } while (var6 > 0);

            } else {
                do {
                    pixels[var1++] = faceLighting[var4 >> 8];
                    var4 += var5;
                    --var6;
                } while (var6 > 0);

            }
        }
    }

    public static void method5(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
        int var7 = 0;
        if (var1 != var0) {
            var7 = (var4 - var3 << 16) / (var1 - var0);
        }

        int var8 = 0;
        if (var2 != var1) {
            var8 = (var5 - var4 << 16) / (var2 - var1);
        }

        int var9 = 0;
        if (var2 != var0) {
            var9 = (var3 - var5 << 16) / (var0 - var2);
        }

        if (var0 <= var1 && var0 <= var2) {
            if (var0 < PanelProperties.height2) {
                if (var1 > PanelProperties.height2) {
                    var1 = PanelProperties.height2;
                }

                if (var2 > PanelProperties.height2) {
                    var2 = PanelProperties.height2;
                }

                if (var1 < var2) {
                    var5 = var3 <<= 16;
                    if (var0 < 0) {
                        var5 = var3 - var9 * var0;
                        var3 -= var7 * var0;
                        var0 = 0;
                    }

                    var4 <<= 16;
                    if (var1 < 0) {
                        var4 -= var8 * var1;
                        var1 = 0;
                    }

                    if (var0 != var1 && var9 < var7 || var0 == var1 && var9 > var8) {
                        var2 -= var1;
                        var1 -= var0;
                        var0 = screen[var0];

                        while (true) {
                            --var1;
                            if (var1 < 0) {
                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        return;
                                    }

                                    method6(PanelProperties.pixels, var0, var6, var5 >> 16, var4 >> 16);
                                    var5 += var9;
                                    var4 += var8;
                                    var0 += PanelProperties.width;
                                }
                            }

                            method6(PanelProperties.pixels, var0, var6, var5 >> 16, var3 >> 16);
                            var5 += var9;
                            var3 += var7;
                            var0 += PanelProperties.width;
                        }
                    } else {
                        var2 -= var1;
                        var1 -= var0;
                        var0 = screen[var0];

                        while (true) {
                            --var1;
                            if (var1 < 0) {
                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        return;
                                    }

                                    method6(PanelProperties.pixels, var0, var6, var4 >> 16, var5 >> 16);
                                    var5 += var9;
                                    var4 += var8;
                                    var0 += PanelProperties.width;
                                }
                            }

                            method6(PanelProperties.pixels, var0, var6, var3 >> 16, var5 >> 16);
                            var5 += var9;
                            var3 += var7;
                            var0 += PanelProperties.width;
                        }
                    }
                } else {
                    var4 = var3 <<= 16;
                    if (var0 < 0) {
                        var4 = var3 - var9 * var0;
                        var3 -= var7 * var0;
                        var0 = 0;
                    }

                    var5 <<= 16;
                    if (var2 < 0) {
                        var5 -= var8 * var2;
                        var2 = 0;
                    }

                    if (var0 != var2 && var9 < var7 || var0 == var2 && var8 > var7) {
                        var1 -= var2;
                        var2 -= var0;
                        var0 = screen[var0];

                        while (true) {
                            --var2;
                            if (var2 < 0) {
                                while (true) {
                                    --var1;
                                    if (var1 < 0) {
                                        return;
                                    }

                                    method6(PanelProperties.pixels, var0, var6, var5 >> 16, var3 >> 16);
                                    var5 += var8;
                                    var3 += var7;
                                    var0 += PanelProperties.width;
                                }
                            }

                            method6(PanelProperties.pixels, var0, var6, var4 >> 16, var3 >> 16);
                            var4 += var9;
                            var3 += var7;
                            var0 += PanelProperties.width;
                        }
                    } else {
                        var1 -= var2;
                        var2 -= var0;
                        var0 = screen[var0];

                        while (true) {
                            --var2;
                            if (var2 < 0) {
                                while (true) {
                                    --var1;
                                    if (var1 < 0) {
                                        return;
                                    }

                                    method6(PanelProperties.pixels, var0, var6, var3 >> 16, var5 >> 16);
                                    var5 += var8;
                                    var3 += var7;
                                    var0 += PanelProperties.width;
                                }
                            }

                            method6(PanelProperties.pixels, var0, var6, var3 >> 16, var4 >> 16);
                            var4 += var9;
                            var3 += var7;
                            var0 += PanelProperties.width;
                        }
                    }
                }
            }
        } else if (var1 <= var2) {
            if (var1 < PanelProperties.height2) {
                if (var2 > PanelProperties.height2) {
                    var2 = PanelProperties.height2;
                }

                if (var0 > PanelProperties.height2) {
                    var0 = PanelProperties.height2;
                }

                if (var2 < var0) {
                    var3 = var4 <<= 16;
                    if (var1 < 0) {
                        var3 -= var7 * var1;
                        var4 -= var8 * var1;
                        var1 = 0;
                    }

                    var5 <<= 16;
                    if (var2 < 0) {
                        var5 -= var9 * var2;
                        var2 = 0;
                    }

                    if (var1 != var2 && var7 < var8 || var1 == var2 && var7 > var9) {
                        var0 -= var2;
                        var2 -= var1;
                        var1 = screen[var1];

                        while (true) {
                            --var2;
                            if (var2 < 0) {
                                while (true) {
                                    --var0;
                                    if (var0 < 0) {
                                        return;
                                    }

                                    method6(PanelProperties.pixels, var1, var6, var3 >> 16, var5 >> 16);
                                    var3 += var7;
                                    var5 += var9;
                                    var1 += PanelProperties.width;
                                }
                            }

                            method6(PanelProperties.pixels, var1, var6, var3 >> 16, var4 >> 16);
                            var3 += var7;
                            var4 += var8;
                            var1 += PanelProperties.width;
                        }
                    } else {
                        var0 -= var2;
                        var2 -= var1;
                        var1 = screen[var1];

                        while (true) {
                            --var2;
                            if (var2 < 0) {
                                while (true) {
                                    --var0;
                                    if (var0 < 0) {
                                        return;
                                    }

                                    method6(PanelProperties.pixels, var1, var6, var5 >> 16, var3 >> 16);
                                    var3 += var7;
                                    var5 += var9;
                                    var1 += PanelProperties.width;
                                }
                            }

                            method6(PanelProperties.pixels, var1, var6, var4 >> 16, var3 >> 16);
                            var3 += var7;
                            var4 += var8;
                            var1 += PanelProperties.width;
                        }
                    }
                } else {
                    var5 = var4 <<= 16;
                    if (var1 < 0) {
                        var5 = var4 - var7 * var1;
                        var4 -= var8 * var1;
                        var1 = 0;
                    }

                    var3 <<= 16;
                    if (var0 < 0) {
                        var3 -= var9 * var0;
                        var0 = 0;
                    }

                    if (var7 < var8) {
                        var2 -= var0;
                        var0 -= var1;
                        var1 = screen[var1];

                        while (true) {
                            --var0;
                            if (var0 < 0) {
                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        return;
                                    }

                                    method6(PanelProperties.pixels, var1, var6, var3 >> 16, var4 >> 16);
                                    var3 += var9;
                                    var4 += var8;
                                    var1 += PanelProperties.width;
                                }
                            }

                            method6(PanelProperties.pixels, var1, var6, var5 >> 16, var4 >> 16);
                            var5 += var7;
                            var4 += var8;
                            var1 += PanelProperties.width;
                        }
                    } else {
                        var2 -= var0;
                        var0 -= var1;
                        var1 = screen[var1];

                        while (true) {
                            --var0;
                            if (var0 < 0) {
                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        return;
                                    }

                                    method6(PanelProperties.pixels, var1, var6, var4 >> 16, var3 >> 16);
                                    var3 += var9;
                                    var4 += var8;
                                    var1 += PanelProperties.width;
                                }
                            }

                            method6(PanelProperties.pixels, var1, var6, var4 >> 16, var5 >> 16);
                            var5 += var7;
                            var4 += var8;
                            var1 += PanelProperties.width;
                        }
                    }
                }
            }
        } else if (var2 < PanelProperties.height2) {
            if (var0 > PanelProperties.height2) {
                var0 = PanelProperties.height2;
            }

            if (var1 > PanelProperties.height2) {
                var1 = PanelProperties.height2;
            }

            if (var0 < var1) {
                var4 = var5 <<= 16;
                if (var2 < 0) {
                    var4 -= var8 * var2;
                    var5 -= var9 * var2;
                    var2 = 0;
                }

                var3 <<= 16;
                if (var0 < 0) {
                    var3 -= var7 * var0;
                    var0 = 0;
                }

                if (var8 < var9) {
                    var1 -= var0;
                    var0 -= var2;
                    var2 = screen[var2];

                    while (true) {
                        --var0;
                        if (var0 < 0) {
                            while (true) {
                                --var1;
                                if (var1 < 0) {
                                    return;
                                }

                                method6(PanelProperties.pixels, var2, var6, var4 >> 16, var3 >> 16);
                                var4 += var8;
                                var3 += var7;
                                var2 += PanelProperties.width;
                            }
                        }

                        method6(PanelProperties.pixels, var2, var6, var4 >> 16, var5 >> 16);
                        var4 += var8;
                        var5 += var9;
                        var2 += PanelProperties.width;
                    }
                } else {
                    var1 -= var0;
                    var0 -= var2;
                    var2 = screen[var2];

                    while (true) {
                        --var0;
                        if (var0 < 0) {
                            while (true) {
                                --var1;
                                if (var1 < 0) {
                                    return;
                                }

                                method6(PanelProperties.pixels, var2, var6, var3 >> 16, var4 >> 16);
                                var4 += var8;
                                var3 += var7;
                                var2 += PanelProperties.width;
                            }
                        }

                        method6(PanelProperties.pixels, var2, var6, var5 >> 16, var4 >> 16);
                        var4 += var8;
                        var5 += var9;
                        var2 += PanelProperties.width;
                    }
                }
            } else {
                var3 = var5 <<= 16;
                if (var2 < 0) {
                    var3 -= var8 * var2;
                    var5 -= var9 * var2;
                    var2 = 0;
                }

                var4 <<= 16;
                if (var1 < 0) {
                    var4 -= var7 * var1;
                    var1 = 0;
                }

                if (var8 < var9) {
                    var0 -= var1;
                    var1 -= var2;
                    var2 = screen[var2];

                    while (true) {
                        --var1;
                        if (var1 < 0) {
                            while (true) {
                                --var0;
                                if (var0 < 0) {
                                    return;
                                }

                                method6(PanelProperties.pixels, var2, var6, var4 >> 16, var5 >> 16);
                                var4 += var7;
                                var5 += var9;
                                var2 += PanelProperties.width;
                            }
                        }

                        method6(PanelProperties.pixels, var2, var6, var3 >> 16, var5 >> 16);
                        var3 += var8;
                        var5 += var9;
                        var2 += PanelProperties.width;
                    }
                } else {
                    var0 -= var1;
                    var1 -= var2;
                    var2 = screen[var2];

                    while (true) {
                        --var1;
                        if (var1 < 0) {
                            while (true) {
                                --var0;
                                if (var0 < 0) {
                                    return;
                                }

                                method6(PanelProperties.pixels, var2, var6, var5 >> 16, var4 >> 16);
                                var4 += var7;
                                var5 += var9;
                                var2 += PanelProperties.width;
                            }
                        }

                        method6(PanelProperties.pixels, var2, var6, var5 >> 16, var3 >> 16);
                        var3 += var8;
                        var5 += var9;
                        var2 += PanelProperties.width;
                    }
                }
            }
        }
    }

    private static void method6(int[] var0, int var1, int var2, int var3, int var4) {
        if (field8) {
            if (var4 > PanelProperties.field65) {
                var4 = PanelProperties.field65;
            }

            if (var3 < 0) {
                var3 = 0;
            }
        }

        if (var3 < var4) {
            var1 += var3;
            int var5 = var4 - var3 >> 2;
            if (field11 == 0) {
                while (true) {
                    --var5;
                    if (var5 < 0) {
                        var5 = var4 - var3 & 3;

                        while (true) {
                            --var5;
                            if (var5 < 0) {
                                return;
                            }

                            var0[var1++] = var2;
                        }
                    }

                    var0[var1++] = var2;
                    var0[var1++] = var2;
                    var0[var1++] = var2;
                    var0[var1++] = var2;
                }
            } else {
                int var6 = field11;
                int var7 = 256 - field11;
                var2 = ((var2 & 16711935) * var7 >> 8 & 16711935) + ((var2 & '\uff00') * var7 >> 8 & '\uff00');
                if (Main.isOptimizedSelected) {
                    while (true) {
                        --var5;
                        if (var5 < 0) {
                            var5 = var4 - var3 & 3;

                            while (true) {
                                --var5;
                                if (var5 < 0) {
                                    return;
                                }

                                var0[var1] = var2 + ((var0[var1] & 16711935) * var6 >> 8 & 16711935) + ((var0[var1] & '\uff00') * var6 >> 8 & '\uff00');
                                ++var1;
                            }
                        }

                        var0[var1] = var2 + ((var0[var1] & 16711935) * var6 >> 8 & 16711935) + ((var0[var1] & '\uff00') * var6 >> 8 & '\uff00');
                        ++var1;
                        var0[var1] = var2 + ((var0[var1] & 16711935) * var6 >> 8 & 16711935) + ((var0[var1] & '\uff00') * var6 >> 8 & '\uff00');
                        ++var1;
                        var0[var1] = var2 + ((var0[var1] & 16711935) * var6 >> 8 & 16711935) + ((var0[var1] & '\uff00') * var6 >> 8 & '\uff00');
                        ++var1;
                        var0[var1] = var2 + ((var0[var1] & 16711935) * var6 >> 8 & 16711935) + ((var0[var1] & '\uff00') * var6 >> 8 & '\uff00');
                        ++var1;
                    }
                } else {
                    while (true) {
                        --var5;
                        if (var5 < 0) {
                            var5 = var4 - var3 & 3;

                            while (true) {
                                --var5;
                                if (var5 < 0) {
                                    return;
                                }

                                var0[var1++] = var2 + ((var0[var1] & 16711935) * var6 >> 8 & 16711935) + ((var0[var1] & '\uff00') * var6 >> 8 & '\uff00');
                            }
                        }

                        var0[var1++] = var2 + ((var0[var1] & 16711935) * var6 >> 8 & 16711935) + ((var0[var1] & '\uff00') * var6 >> 8 & '\uff00');
                        var0[var1++] = var2 + ((var0[var1] & 16711935) * var6 >> 8 & 16711935) + ((var0[var1] & '\uff00') * var6 >> 8 & '\uff00');
                        var0[var1++] = var2 + ((var0[var1] & 16711935) * var6 >> 8 & 16711935) + ((var0[var1] & '\uff00') * var6 >> 8 & '\uff00');
                        var0[var1++] = var2 + ((var0[var1] & 16711935) * var6 >> 8 & 16711935) + ((var0[var1] & '\uff00') * var6 >> 8 & '\uff00');
                    }
                }
            }
        }
    }

    public static void method7(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17, int var18) {
        field9 = !field19[var18];
        var10 = var9 - var10;
        var13 = var12 - var13;
        var16 = var15 - var16;
        var11 -= var9;
        var14 -= var12;
        var17 -= var15;
        var18 = var11 * var12 - var14 * var9 << 14;
        int var19 = var14 * var15 - var17 * var12 << 8;
        int var20 = var17 * var9 - var11 * var15 << 5;
        int var21 = var10 * var12 - var13 * var9 << 14;
        var12 = var13 * var15 - var16 * var12 << 8;
        var9 = var16 * var9 - var10 * var15 << 5;
        var15 = var13 * var11 - var10 * var14 << 14;
        var13 = var16 * var14 - var13 * var17 << 8;
        var10 = var10 * var17 - var16 * var11 << 5;
        var11 = 0;
        var14 = 0;
        if (var1 != var0) {
            var11 = (var4 - var3 << 16) / (var1 - var0);
            var14 = (var7 - var6 << 16) / (var1 - var0);
        }

        var16 = 0;
        var17 = 0;
        if (var2 != var1) {
            var16 = (var5 - var4 << 16) / (var2 - var1);
            var17 = (var8 - var7 << 16) / (var2 - var1);
        }

        int var22 = 0;
        int var23 = 0;
        if (var2 != var0) {
            var22 = (var3 - var5 << 16) / (var0 - var2);
            var23 = (var6 - var8 << 16) / (var0 - var2);
        }

        int var24;
        if (var0 <= var1 && var0 <= var2) {
            if (var0 < PanelProperties.height2) {
                if (var1 > PanelProperties.height2) {
                    var1 = PanelProperties.height2;
                }

                if (var2 > PanelProperties.height2) {
                    var2 = PanelProperties.height2;
                }

                if (var1 < var2) {
                    var5 = var3 <<= 16;
                    var8 = var6 <<= 16;
                    if (var0 < 0) {
                        var5 = var3 - var22 * var0;
                        var3 -= var11 * var0;
                        var8 = var6 - var23 * var0;
                        var6 -= var14 * var0;
                        var0 = 0;
                    }

                    var4 <<= 16;
                    var7 <<= 16;
                    if (var1 < 0) {
                        var4 -= var16 * var1;
                        var7 -= var17 * var1;
                        var1 = 0;
                    }

                    var24 = var0 - screenPosY;
                    var18 += var20 * var24;
                    var21 += var9 * var24;
                    var15 += var10 * var24;
                    if (var0 != var1 && var22 < var11 || var0 == var1 && var22 > var16) {
                        var2 -= var1;
                        var1 -= var0;
                        var0 = screen[var0];

                        while (true) {
                            --var1;
                            if (var1 < 0) {
                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        return;
                                    }

                                    method8(PanelProperties.pixels, null, var0, var5 >> 16, var4 >> 16, var8 >> 8, var7 >> 8, var18, var21, var15, var19, var12, var13);
                                    var5 += var22;
                                    var4 += var16;
                                    var8 += var23;
                                    var7 += var17;
                                    var0 += PanelProperties.width;
                                    var18 += var20;
                                    var21 += var9;
                                    var15 += var10;
                                }
                            }

                            method8(PanelProperties.pixels, null, var0, var5 >> 16, var3 >> 16, var8 >> 8, var6 >> 8, var18, var21, var15, var19, var12, var13);
                            var5 += var22;
                            var3 += var11;
                            var8 += var23;
                            var6 += var14;
                            var0 += PanelProperties.width;
                            var18 += var20;
                            var21 += var9;
                            var15 += var10;
                        }
                    } else {
                        var2 -= var1;
                        var1 -= var0;
                        var0 = screen[var0];

                        while (true) {
                            --var1;
                            if (var1 < 0) {
                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        return;
                                    }

                                    method8(PanelProperties.pixels, null, var0, var4 >> 16, var5 >> 16, var7 >> 8, var8 >> 8, var18, var21, var15, var19, var12, var13);
                                    var5 += var22;
                                    var4 += var16;
                                    var8 += var23;
                                    var7 += var17;
                                    var0 += PanelProperties.width;
                                    var18 += var20;
                                    var21 += var9;
                                    var15 += var10;
                                }
                            }

                            method8(PanelProperties.pixels, null, var0, var3 >> 16, var5 >> 16, var6 >> 8, var8 >> 8, var18, var21, var15, var19, var12, var13);
                            var5 += var22;
                            var3 += var11;
                            var8 += var23;
                            var6 += var14;
                            var0 += PanelProperties.width;
                            var18 += var20;
                            var21 += var9;
                            var15 += var10;
                        }
                    }
                } else {
                    var4 = var3 <<= 16;
                    var7 = var6 <<= 16;
                    if (var0 < 0) {
                        var4 = var3 - var22 * var0;
                        var3 -= var11 * var0;
                        var7 = var6 - var23 * var0;
                        var6 -= var14 * var0;
                        var0 = 0;
                    }

                    var5 <<= 16;
                    var8 <<= 16;
                    if (var2 < 0) {
                        var5 -= var16 * var2;
                        var8 -= var17 * var2;
                        var2 = 0;
                    }

                    var24 = var0 - screenPosY;
                    var18 += var20 * var24;
                    var21 += var9 * var24;
                    var15 += var10 * var24;
                    if ((var0 == var2 || var22 >= var11) && (var0 != var2 || var16 <= var11)) {
                        var1 -= var2;
                        var2 -= var0;
                        var0 = screen[var0];

                        while (true) {
                            --var2;
                            if (var2 < 0) {
                                while (true) {
                                    --var1;
                                    if (var1 < 0) {
                                        return;
                                    }

                                    method8(PanelProperties.pixels, null, var0, var3 >> 16, var5 >> 16, var6 >> 8, var8 >> 8, var18, var21, var15, var19, var12, var13);
                                    var5 += var16;
                                    var3 += var11;
                                    var8 += var17;
                                    var6 += var14;
                                    var0 += PanelProperties.width;
                                    var18 += var20;
                                    var21 += var9;
                                    var15 += var10;
                                }
                            }

                            method8(PanelProperties.pixels, null, var0, var3 >> 16, var4 >> 16, var6 >> 8, var7 >> 8, var18, var21, var15, var19, var12, var13);
                            var4 += var22;
                            var3 += var11;
                            var7 += var23;
                            var6 += var14;
                            var0 += PanelProperties.width;
                            var18 += var20;
                            var21 += var9;
                            var15 += var10;
                        }
                    } else {
                        var1 -= var2;
                        var2 -= var0;
                        var0 = screen[var0];

                        while (true) {
                            --var2;
                            if (var2 < 0) {
                                while (true) {
                                    --var1;
                                    if (var1 < 0) {
                                        return;
                                    }

                                    method8(PanelProperties.pixels, null, var0, var5 >> 16, var3 >> 16, var8 >> 8, var6 >> 8, var18, var21, var15, var19, var12, var13);
                                    var5 += var16;
                                    var3 += var11;
                                    var8 += var17;
                                    var6 += var14;
                                    var0 += PanelProperties.width;
                                    var18 += var20;
                                    var21 += var9;
                                    var15 += var10;
                                }
                            }

                            method8(PanelProperties.pixels, null, var0, var4 >> 16, var3 >> 16, var7 >> 8, var6 >> 8, var18, var21, var15, var19, var12, var13);
                            var4 += var22;
                            var3 += var11;
                            var7 += var23;
                            var6 += var14;
                            var0 += PanelProperties.width;
                            var18 += var20;
                            var21 += var9;
                            var15 += var10;
                        }
                    }
                }
            }
        } else if (var1 <= var2) {
            if (var1 < PanelProperties.height2) {
                if (var2 > PanelProperties.height2) {
                    var2 = PanelProperties.height2;
                }

                if (var0 > PanelProperties.height2) {
                    var0 = PanelProperties.height2;
                }

                if (var2 < var0) {
                    var3 = var4 <<= 16;
                    var6 = var7 <<= 16;
                    if (var1 < 0) {
                        var3 -= var11 * var1;
                        var4 -= var16 * var1;
                        var6 -= var14 * var1;
                        var7 -= var17 * var1;
                        var1 = 0;
                    }

                    var5 <<= 16;
                    var8 <<= 16;
                    if (var2 < 0) {
                        var5 -= var22 * var2;
                        var8 -= var23 * var2;
                        var2 = 0;
                    }

                    var24 = var1 - screenPosY;
                    var18 += var20 * var24;
                    var21 += var9 * var24;
                    var15 += var10 * var24;
                    if (var1 != var2 && var11 < var16 || var1 == var2 && var11 > var22) {
                        var0 -= var2;
                        var2 -= var1;
                        var1 = screen[var1];

                        while (true) {
                            --var2;
                            if (var2 < 0) {
                                while (true) {
                                    --var0;
                                    if (var0 < 0) {
                                        return;
                                    }

                                    method8(PanelProperties.pixels, null, var1, var3 >> 16, var5 >> 16, var6 >> 8, var8 >> 8, var18, var21, var15, var19, var12, var13);
                                    var3 += var11;
                                    var5 += var22;
                                    var6 += var14;
                                    var8 += var23;
                                    var1 += PanelProperties.width;
                                    var18 += var20;
                                    var21 += var9;
                                    var15 += var10;
                                }
                            }

                            method8(PanelProperties.pixels, null, var1, var3 >> 16, var4 >> 16, var6 >> 8, var7 >> 8, var18, var21, var15, var19, var12, var13);
                            var3 += var11;
                            var4 += var16;
                            var6 += var14;
                            var7 += var17;
                            var1 += PanelProperties.width;
                            var18 += var20;
                            var21 += var9;
                            var15 += var10;
                        }
                    } else {
                        var0 -= var2;
                        var2 -= var1;
                        var1 = screen[var1];

                        while (true) {
                            --var2;
                            if (var2 < 0) {
                                while (true) {
                                    --var0;
                                    if (var0 < 0) {
                                        return;
                                    }

                                    method8(PanelProperties.pixels, null, var1, var5 >> 16, var3 >> 16, var8 >> 8, var6 >> 8, var18, var21, var15, var19, var12, var13);
                                    var3 += var11;
                                    var5 += var22;
                                    var6 += var14;
                                    var8 += var23;
                                    var1 += PanelProperties.width;
                                    var18 += var20;
                                    var21 += var9;
                                    var15 += var10;
                                }
                            }

                            method8(PanelProperties.pixels, null, var1, var4 >> 16, var3 >> 16, var7 >> 8, var6 >> 8, var18, var21, var15, var19, var12, var13);
                            var3 += var11;
                            var4 += var16;
                            var6 += var14;
                            var7 += var17;
                            var1 += PanelProperties.width;
                            var18 += var20;
                            var21 += var9;
                            var15 += var10;
                        }
                    }
                } else {
                    var5 = var4 <<= 16;
                    var8 = var7 <<= 16;
                    if (var1 < 0) {
                        var5 = var4 - var11 * var1;
                        var4 -= var16 * var1;
                        var8 = var7 - var14 * var1;
                        var7 -= var17 * var1;
                        var1 = 0;
                    }

                    var3 <<= 16;
                    var6 <<= 16;
                    if (var0 < 0) {
                        var3 -= var22 * var0;
                        var6 -= var23 * var0;
                        var0 = 0;
                    }

                    var24 = var1 - screenPosY;
                    var18 += var20 * var24;
                    var21 += var9 * var24;
                    var15 += var10 * var24;
                    if (var11 < var16) {
                        var2 -= var0;
                        var0 -= var1;
                        var1 = screen[var1];

                        while (true) {
                            --var0;
                            if (var0 < 0) {
                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        return;
                                    }

                                    method8(PanelProperties.pixels, null, var1, var3 >> 16, var4 >> 16, var6 >> 8, var7 >> 8, var18, var21, var15, var19, var12, var13);
                                    var3 += var22;
                                    var4 += var16;
                                    var6 += var23;
                                    var7 += var17;
                                    var1 += PanelProperties.width;
                                    var18 += var20;
                                    var21 += var9;
                                    var15 += var10;
                                }
                            }

                            method8(PanelProperties.pixels, null, var1, var5 >> 16, var4 >> 16, var8 >> 8, var7 >> 8, var18, var21, var15, var19, var12, var13);
                            var5 += var11;
                            var4 += var16;
                            var8 += var14;
                            var7 += var17;
                            var1 += PanelProperties.width;
                            var18 += var20;
                            var21 += var9;
                            var15 += var10;
                        }
                    } else {
                        var2 -= var0;
                        var0 -= var1;
                        var1 = screen[var1];

                        while (true) {
                            --var0;
                            if (var0 < 0) {
                                while (true) {
                                    --var2;
                                    if (var2 < 0) {
                                        return;
                                    }

                                    method8(PanelProperties.pixels, null, var1, var4 >> 16, var3 >> 16, var7 >> 8, var6 >> 8, var18, var21, var15, var19, var12, var13);
                                    var3 += var22;
                                    var4 += var16;
                                    var6 += var23;
                                    var7 += var17;
                                    var1 += PanelProperties.width;
                                    var18 += var20;
                                    var21 += var9;
                                    var15 += var10;
                                }
                            }

                            method8(PanelProperties.pixels, null, var1, var4 >> 16, var5 >> 16, var7 >> 8, var8 >> 8, var18, var21, var15, var19, var12, var13);
                            var5 += var11;
                            var4 += var16;
                            var8 += var14;
                            var7 += var17;
                            var1 += PanelProperties.width;
                            var18 += var20;
                            var21 += var9;
                            var15 += var10;
                        }
                    }
                }
            }
        } else if (var2 < PanelProperties.height2) {
            if (var0 > PanelProperties.height2) {
                var0 = PanelProperties.height2;
            }

            if (var1 > PanelProperties.height2) {
                var1 = PanelProperties.height2;
            }

            if (var0 < var1) {
                var4 = var5 <<= 16;
                var7 = var8 <<= 16;
                if (var2 < 0) {
                    var4 -= var16 * var2;
                    var5 -= var22 * var2;
                    var7 -= var17 * var2;
                    var8 -= var23 * var2;
                    var2 = 0;
                }

                var3 <<= 16;
                var6 <<= 16;
                if (var0 < 0) {
                    var3 -= var11 * var0;
                    var6 -= var14 * var0;
                    var0 = 0;
                }

                var24 = var2 - screenPosY;
                var18 += var20 * var24;
                var21 += var9 * var24;
                var15 += var10 * var24;
                if (var16 < var22) {
                    var1 -= var0;
                    var0 -= var2;
                    var2 = screen[var2];

                    while (true) {
                        --var0;
                        if (var0 < 0) {
                            while (true) {
                                --var1;
                                if (var1 < 0) {
                                    return;
                                }

                                method8(PanelProperties.pixels, null, var2, var4 >> 16, var3 >> 16, var7 >> 8, var6 >> 8, var18, var21, var15, var19, var12, var13);
                                var4 += var16;
                                var3 += var11;
                                var7 += var17;
                                var6 += var14;
                                var2 += PanelProperties.width;
                                var18 += var20;
                                var21 += var9;
                                var15 += var10;
                            }
                        }

                        method8(PanelProperties.pixels, null, var2, var4 >> 16, var5 >> 16, var7 >> 8, var8 >> 8, var18, var21, var15, var19, var12, var13);
                        var4 += var16;
                        var5 += var22;
                        var7 += var17;
                        var8 += var23;
                        var2 += PanelProperties.width;
                        var18 += var20;
                        var21 += var9;
                        var15 += var10;
                    }
                } else {
                    var1 -= var0;
                    var0 -= var2;
                    var2 = screen[var2];

                    while (true) {
                        --var0;
                        if (var0 < 0) {
                            while (true) {
                                --var1;
                                if (var1 < 0) {
                                    return;
                                }

                                method8(PanelProperties.pixels, null, var2, var3 >> 16, var4 >> 16, var6 >> 8, var7 >> 8, var18, var21, var15, var19, var12, var13);
                                var4 += var16;
                                var3 += var11;
                                var7 += var17;
                                var6 += var14;
                                var2 += PanelProperties.width;
                                var18 += var20;
                                var21 += var9;
                                var15 += var10;
                            }
                        }

                        method8(PanelProperties.pixels, null, var2, var5 >> 16, var4 >> 16, var8 >> 8, var7 >> 8, var18, var21, var15, var19, var12, var13);
                        var4 += var16;
                        var5 += var22;
                        var7 += var17;
                        var8 += var23;
                        var2 += PanelProperties.width;
                        var18 += var20;
                        var21 += var9;
                        var15 += var10;
                    }
                }
            } else {
                var3 = var5 <<= 16;
                var6 = var8 <<= 16;
                if (var2 < 0) {
                    var3 -= var16 * var2;
                    var5 -= var22 * var2;
                    var6 -= var17 * var2;
                    var8 -= var23 * var2;
                    var2 = 0;
                }

                var4 <<= 16;
                var7 <<= 16;
                if (var1 < 0) {
                    var4 -= var11 * var1;
                    var7 -= var14 * var1;
                    var1 = 0;
                }

                var24 = var2 - screenPosY;
                var18 += var20 * var24;
                var21 += var9 * var24;
                var15 += var10 * var24;
                if (var16 < var22) {
                    var0 -= var1;
                    var1 -= var2;
                    var2 = screen[var2];

                    while (true) {
                        --var1;
                        if (var1 < 0) {
                            while (true) {
                                --var0;
                                if (var0 < 0) {
                                    return;
                                }

                                method8(PanelProperties.pixels, null, var2, var4 >> 16, var5 >> 16, var7 >> 8, var8 >> 8, var18, var21, var15, var19, var12, var13);
                                var4 += var11;
                                var5 += var22;
                                var7 += var14;
                                var8 += var23;
                                var2 += PanelProperties.width;
                                var18 += var20;
                                var21 += var9;
                                var15 += var10;
                            }
                        }

                        method8(PanelProperties.pixels, null, var2, var3 >> 16, var5 >> 16, var6 >> 8, var8 >> 8, var18, var21, var15, var19, var12, var13);
                        var3 += var16;
                        var5 += var22;
                        var6 += var17;
                        var8 += var23;
                        var2 += PanelProperties.width;
                        var18 += var20;
                        var21 += var9;
                        var15 += var10;
                    }
                } else {
                    var0 -= var1;
                    var1 -= var2;
                    var2 = screen[var2];

                    while (true) {
                        --var1;
                        if (var1 < 0) {
                            while (true) {
                                --var0;
                                if (var0 < 0) {
                                    return;
                                }

                                method8(PanelProperties.pixels, null, var2, var5 >> 16, var4 >> 16, var8 >> 8, var7 >> 8, var18, var21, var15, var19, var12, var13);
                                var4 += var11;
                                var5 += var22;
                                var7 += var14;
                                var8 += var23;
                                var2 += PanelProperties.width;
                                var18 += var20;
                                var21 += var9;
                                var15 += var10;
                            }
                        }

                        method8(PanelProperties.pixels, null, var2, var5 >> 16, var3 >> 16, var8 >> 8, var6 >> 8, var18, var21, var15, var19, var12, var13);
                        var3 += var16;
                        var5 += var22;
                        var6 += var17;
                        var8 += var23;
                        var2 += PanelProperties.width;
                        var18 += var20;
                        var21 += var9;
                        var15 += var10;
                    }
                }
            }
        }
    }

    private static void method8(int[] var0, int[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12) {
        int var13 = 0;
        int var14 = 0;
        if (var3 < var4) {
            int var15;
            if (field8) {
                var6 = (var6 - var5) / (var4 - var3);
                if (var4 > PanelProperties.field65) {
                    var4 = PanelProperties.field65;
                }

                if (var3 < 0) {
                    var5 -= var3 * var6;
                    var3 = 0;
                }

                if (var3 >= var4) {
                    return;
                }

                var15 = var4 - var3 >> 3;
                var6 <<= 12;
            } else if (var4 - var3 > 7) {
                var15 = var4 - var3 >> 3;
                var6 = (var6 - var5) * field14[var15] >> 6;
            } else {
                var15 = 0;
                var6 = 0;
            }

            var5 <<= 9;
            var2 += var3;
            int var16;
            int var17;
            int var18;
            int var19;
            int var20;
            int var21;
            int var22;
            if (field7) {
                var16 = 0;
                var17 = 0;
                var18 = var3 - screenPosX;
                var7 += (var10 >> 3) * var18;
                var8 += (var11 >> 3) * var18;
                if ((var18 = (var9 += (var12 >> 3) * var18) >> 12) != 0) {
                    var13 = var7 / var18;
                    var14 = var8 / var18;
                    if (var13 < 0) {
                        var13 = 0;
                    } else if (var13 > 4032) {
                        var13 = 4032;
                    }
                }

                var7 += var10;
                var8 += var11;
                if ((var18 = (var9 += var12) >> 12) != 0) {
                    var16 = var7 / var18;
                    var17 = var8 / var18;
                    if (var16 < 7) {
                        var16 = 7;
                    } else if (var16 > 4032) {
                        var16 = 4032;
                    }
                }

                var18 = var16 - var13 >> 3;
                var19 = var17 - var14 >> 3;
                var13 += (var5 & 6291456) >> 3;
                var20 = var5 >> 23;
                if (!field9) {
                    while (var15-- > 0) {
                        if ((var21 = var1[(var14 & 4032) + (var13 >> 6)] >>> var20) != 0) {
                            var0[var2] = var21;
                        }

                        ++var2;
                        var13 += var18;
                        var14 += var19;
                        if ((var21 = var1[(var14 & 4032) + (var13 >> 6)] >>> var20) != 0) {
                            var0[var2] = var21;
                        }

                        ++var2;
                        var13 += var18;
                        var14 += var19;
                        if ((var21 = var1[(var14 & 4032) + (var13 >> 6)] >>> var20) != 0) {
                            var0[var2] = var21;
                        }

                        ++var2;
                        var13 += var18;
                        var14 += var19;
                        if ((var21 = var1[(var14 & 4032) + (var13 >> 6)] >>> var20) != 0) {
                            var0[var2] = var21;
                        }

                        ++var2;
                        var13 += var18;
                        var14 += var19;
                        if ((var21 = var1[(var14 & 4032) + (var13 >> 6)] >>> var20) != 0) {
                            var0[var2] = var21;
                        }

                        ++var2;
                        var13 += var18;
                        var14 += var19;
                        if ((var21 = var1[(var14 & 4032) + (var13 >> 6)] >>> var20) != 0) {
                            var0[var2] = var21;
                        }

                        ++var2;
                        var13 += var18;
                        var14 += var19;
                        if ((var21 = var1[(var14 & 4032) + (var13 >> 6)] >>> var20) != 0) {
                            var0[var2] = var21;
                        }

                        ++var2;
                        var13 += var18;
                        var14 += var19;
                        if ((var21 = var1[(var14 & 4032) + (var13 >> 6)] >>> var20) != 0) {
                            var0[var2] = var21;
                        }

                        ++var2;
                        var13 = var16;
                        var14 = var17;
                        var7 += var10;
                        var8 += var11;
                        if ((var22 = (var9 += var12) >> 12) != 0) {
                            var16 = var7 / var22;
                            var17 = var8 / var22;
                            if (var16 < 7) {
                                var16 = 7;
                            } else if (var16 > 4032) {
                                var16 = 4032;
                            }
                        }

                        var18 = var16 - var13 >> 3;
                        var19 = var17 - var14 >> 3;
                        var5 += var6;
                        var13 += (var5 & 6291456) >> 3;
                        var20 = var5 >> 23;
                    }

                    for (var15 = var4 - var3 & 7; var15-- > 0; var14 += var19) {
                        if ((var21 = var1[(var14 & 4032) + (var13 >> 6)] >>> var20) != 0) {
                            var0[var2] = var21;
                        }

                        ++var2;
                        var13 += var18;
                    }

                } else {
                    while (var15-- > 0) {
                        var0[var2++] = var1[(var14 & 4032) + (var13 >> 6)] >>> var20;
                        var13 += var18;
                        var14 += var19;
                        var0[var2++] = var1[(var14 & 4032) + (var13 >> 6)] >>> var20;
                        var13 += var18;
                        var14 += var19;
                        var0[var2++] = var1[(var14 & 4032) + (var13 >> 6)] >>> var20;
                        var13 += var18;
                        var14 += var19;
                        var0[var2++] = var1[(var14 & 4032) + (var13 >> 6)] >>> var20;
                        var13 += var18;
                        var14 += var19;
                        var0[var2++] = var1[(var14 & 4032) + (var13 >> 6)] >>> var20;
                        var13 += var18;
                        var14 += var19;
                        var0[var2++] = var1[(var14 & 4032) + (var13 >> 6)] >>> var20;
                        var13 += var18;
                        var14 += var19;
                        var0[var2++] = var1[(var14 & 4032) + (var13 >> 6)] >>> var20;
                        var13 += var18;
                        var14 += var19;
                        var0[var2++] = var1[(var14 & 4032) + (var13 >> 6)] >>> var20;
                        var13 = var16;
                        var14 = var17;
                        var7 += var10;
                        var8 += var11;
                        if ((var21 = (var9 += var12) >> 12) != 0) {
                            var16 = var7 / var21;
                            var17 = var8 / var21;
                            if (var16 < 7) {
                                var16 = 7;
                            } else if (var16 > 4032) {
                                var16 = 4032;
                            }
                        }

                        var18 = var16 - var13 >> 3;
                        var19 = var17 - var14 >> 3;
                        var5 += var6;
                        var13 += (var5 & 6291456) >> 3;
                        var20 = var5 >> 23;
                    }

                    for (var15 = var4 - var3 & 7; var15-- > 0; var14 += var19) {
                        var0[var2++] = var1[(var14 & 4032) + (var13 >> 6)] >>> var20;
                        var13 += var18;
                    }

                }
            } else {
                var16 = 0;
                var17 = 0;
                var18 = var3 - screenPosX;
                var7 += (var10 >> 3) * var18;
                var8 += (var11 >> 3) * var18;
                if ((var18 = (var9 += (var12 >> 3) * var18) >> 14) != 0) {
                    var13 = var7 / var18;
                    var14 = var8 / var18;
                    if (var13 < 0) {
                        var13 = 0;
                    } else if (var13 > 16256) {
                        var13 = 16256;
                    }
                }

                var7 += var10;
                var8 += var11;
                if ((var18 = (var9 += var12) >> 14) != 0) {
                    var16 = var7 / var18;
                    var17 = var8 / var18;
                    if (var16 < 7) {
                        var16 = 7;
                    } else if (var16 > 16256) {
                        var16 = 16256;
                    }
                }

                var18 = var16 - var13 >> 3;
                var19 = var17 - var14 >> 3;
                var13 += var5 & 6291456;
                var20 = var5 >> 23;
                if (!field9) {
                    while (var15-- > 0) {
                        if ((var21 = var1[(var14 & 16256) + (var13 >> 7)] >>> var20) != 0) {
                            var0[var2] = var21;
                        }

                        ++var2;
                        var13 += var18;
                        var14 += var19;
                        if ((var21 = var1[(var14 & 16256) + (var13 >> 7)] >>> var20) != 0) {
                            var0[var2] = var21;
                        }

                        ++var2;
                        var13 += var18;
                        var14 += var19;
                        if ((var21 = var1[(var14 & 16256) + (var13 >> 7)] >>> var20) != 0) {
                            var0[var2] = var21;
                        }

                        ++var2;
                        var13 += var18;
                        var14 += var19;
                        if ((var21 = var1[(var14 & 16256) + (var13 >> 7)] >>> var20) != 0) {
                            var0[var2] = var21;
                        }

                        ++var2;
                        var13 += var18;
                        var14 += var19;
                        if ((var21 = var1[(var14 & 16256) + (var13 >> 7)] >>> var20) != 0) {
                            var0[var2] = var21;
                        }

                        ++var2;
                        var13 += var18;
                        var14 += var19;
                        if ((var21 = var1[(var14 & 16256) + (var13 >> 7)] >>> var20) != 0) {
                            var0[var2] = var21;
                        }

                        ++var2;
                        var13 += var18;
                        var14 += var19;
                        if ((var21 = var1[(var14 & 16256) + (var13 >> 7)] >>> var20) != 0) {
                            var0[var2] = var21;
                        }

                        ++var2;
                        var13 += var18;
                        var14 += var19;
                        if ((var21 = var1[(var14 & 16256) + (var13 >> 7)] >>> var20) != 0) {
                            var0[var2] = var21;
                        }

                        ++var2;
                        var13 = var16;
                        var14 = var17;
                        var7 += var10;
                        var8 += var11;
                        if ((var22 = (var9 += var12) >> 14) != 0) {
                            var16 = var7 / var22;
                            var17 = var8 / var22;
                            if (var16 < 7) {
                                var16 = 7;
                            } else if (var16 > 16256) {
                                var16 = 16256;
                            }
                        }

                        var18 = var16 - var13 >> 3;
                        var19 = var17 - var14 >> 3;
                        var5 += var6;
                        var13 += var5 & 6291456;
                        var20 = var5 >> 23;
                    }

                    for (var21 = var4 - var3 & 7; var21-- > 0; var14 += var19) {
                        if ((var22 = var1[(var14 & 16256) + (var13 >> 7)] >>> var20) != 0) {
                            var0[var2] = var22;
                        }

                        ++var2;
                        var13 += var18;
                    }

                } else {
                    while (var15-- > 0) {
                        var0[var2++] = var1[(var14 & 16256) + (var13 >> 7)] >>> var20;
                        var13 += var18;
                        var14 += var19;
                        var0[var2++] = var1[(var14 & 16256) + (var13 >> 7)] >>> var20;
                        var13 += var18;
                        var14 += var19;
                        var0[var2++] = var1[(var14 & 16256) + (var13 >> 7)] >>> var20;
                        var13 += var18;
                        var14 += var19;
                        var0[var2++] = var1[(var14 & 16256) + (var13 >> 7)] >>> var20;
                        var13 += var18;
                        var14 += var19;
                        var0[var2++] = var1[(var14 & 16256) + (var13 >> 7)] >>> var20;
                        var13 += var18;
                        var14 += var19;
                        var0[var2++] = var1[(var14 & 16256) + (var13 >> 7)] >>> var20;
                        var13 += var18;
                        var14 += var19;
                        var0[var2++] = var1[(var14 & 16256) + (var13 >> 7)] >>> var20;
                        var13 += var18;
                        var14 += var19;
                        var0[var2++] = var1[(var14 & 16256) + (var13 >> 7)] >>> var20;
                        var13 = var16;
                        var14 = var17;
                        var7 += var10;
                        var8 += var11;
                        if ((var21 = (var9 += var12) >> 14) != 0) {
                            var16 = var7 / var21;
                            var17 = var8 / var21;
                            if (var16 < 7) {
                                var16 = 7;
                            } else if (var16 > 16256) {
                                var16 = 16256;
                            }
                        }

                        var18 = var16 - var13 >> 3;
                        var19 = var17 - var14 >> 3;
                        var5 += var6;
                        var13 += var5 & 6291456;
                        var20 = var5 >> 23;
                    }

                    for (var15 = var4 - var3 & 7; var15-- > 0; var14 += var19) {
                        var0[var2++] = var1[(var14 & 16256) + (var13 >> 7)] >>> var20;
                        var13 += var18;
                    }

                }
            }
        }
    }
}
