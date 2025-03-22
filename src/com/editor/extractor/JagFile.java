/*    */
package com.editor.extractor;

import com.alex.io.ByteStream;

/*    */
/*    */ public class JagFile
        /*    */ {
    /*    */   public byte[] fbytes;
    /*    */   public int datasize;
    /*    */   public int[] filename;
    /*    */   public int[] filesize;
    /*    */   public int[] filelength;
    /*    */   public int[] filestart;
    /*    */   private boolean decompressed;

    /*    */
    /*    */
    public JagFile(byte[] paramArrayOfByte)
    /*    */ {
        /* 17 */
        loadstream(paramArrayOfByte);
        /*    */
    }

    /*    */
    /*    */
    private void loadstream(byte[] paramArrayOfByte)
    /*    */ {
        /* 22 */
        ByteStream localByteStream = new ByteStream(paramArrayOfByte);
        /* 23 */
        int i = localByteStream.read3Bytes();
        /* 24 */
        int j = localByteStream.read3Bytes();
        /* 25 */
        if (j != i)
            /*    */ {
            /* 27 */
            byte[] arrayOfByte = new byte[i];
            /* 28 */
            PackJAG.method73(arrayOfByte, i, paramArrayOfByte, j, 6);
            /* 29 */
            this.fbytes = arrayOfByte;
            /* 30 */
            localByteStream = new ByteStream(this.fbytes);
            /* 31 */
            this.decompressed = true;
            /*    */
        }
        /*    */
        else {
            /* 34 */
            this.fbytes = paramArrayOfByte;
            /* 35 */
            this.decompressed = false;
            /*    */
        }
        /* 37 */
        this.datasize = localByteStream.readUnsignedWord();
        /* 38 */
        this.filename = new int[this.datasize];
        /* 39 */
        this.filesize = new int[this.datasize];
        /* 40 */
        this.filelength = new int[this.datasize];
        /* 41 */
        this.filestart = new int[this.datasize];
        /* 42 */
        int k = localByteStream.Offset + this.datasize * 10;
        /* 43 */
        for (int m = 0; m < this.datasize; m++)
            /*    */ {
            /* 45 */
            this.filename[m] = localByteStream.readDWord();
            /* 46 */
            this.filesize[m] = localByteStream.read3Bytes();
            /* 47 */
            this.filelength[m] = localByteStream.read3Bytes();
            /* 48 */
            this.filestart[m] = k;
            /* 49 */
            k += this.filelength[m];
            /*    */
        }
        /*    */
    }

    /*    */
    /*    */
    public byte[] readfile(String paramString, byte[] paramArrayOfByte)
    /*    */ {
        /* 56 */
        int i = 0;
        /* 57 */
        paramString = paramString.toUpperCase();
        /* 58 */
        for (int j = 0; j < paramString.length(); j++) {
            /* 59 */
            i = i * 61 + paramString.charAt(j) - 32;
            /*    */
        }
        /* 61 */
        for (int j = 0; j < this.datasize; j++) {
            /* 62 */
            if (this.filename[j] == i)
                /*    */ {
                /* 64 */
                if (paramArrayOfByte == null)
                    /* 65 */ paramArrayOfByte = new byte[this.filesize[j]];
                /* 66 */
                if (!this.decompressed)
                    /*    */ {
                    /* 68 */
                    PackJAG.method73(paramArrayOfByte, this.filesize[j], this.fbytes, this.filelength[j], this.filestart[j]);
                    /*    */
                }
                /*    */
                else {
                    /* 71 */
                    /* 72 */
                    /*    */
                    if (this.filesize[j] >= 0)
                        System.arraycopy(this.fbytes, this.filestart[j] + 0, paramArrayOfByte, 0, this.filesize[j]);
                    /*    */
                }
                /* 75 */
                return paramArrayOfByte;
                /*    */
            }
            /*    */
        }
        /* 78 */
        return null;
        /*    */
    }

    /*    */
    /*    */
    public byte[] readfile(int paramInt, byte[] paramArrayOfByte)
    /*    */ {
        /* 83 */
        if (paramArrayOfByte == null)
            /* 84 */ paramArrayOfByte = new byte[this.filesize[paramInt]];
        /* 85 */
        if (!this.decompressed)
            /*    */ {
            /* 87 */
            PackJAG.method73(paramArrayOfByte, this.filesize[paramInt], this.fbytes, this.filelength[paramInt], this.filestart[paramInt]);
            /*    */
        }
        /*    */
        else {
            /* 90 */
            /* 91 */
            /*    */
            if (this.filesize[paramInt] >= 0)
                System.arraycopy(this.fbytes, this.filestart[paramInt] + 0, paramArrayOfByte, 0, this.filesize[paramInt]);
            /*    */
        }
        /* 94 */
        return paramArrayOfByte;
        /*    */
    }
    /*    */
}

/* Location:           C:\Users\HaiderPC\Desktop\Cache Extractor\data\CacheExport2.jar
 * Qualified Name:     cacheexport2.JagFile
 * JD-Core Version:    0.6.2
 */