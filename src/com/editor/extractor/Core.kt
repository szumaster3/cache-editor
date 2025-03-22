package com.editor.extractor

import com.alex.util.Utils
import console.Main
import java.io.RandomAccessFile

object Core {
    var fFound: BooleanArray = BooleanArray(0)
    var fInts: IntArray = IntArray(0)
    var fNames: Array<String?> = arrayOfNulls(0)
    var InputDir: String = "./obj/"
    var OutputDir: String = "./out/"
    var Cache: Array<CacheFile?>? = null
    var CacheType: Array<String?>? = null
    var CacheExt: Array<String?>? = null
    var CacheData: RandomAccessFile? = null
    var cacheVersion: String = ""
    var wantSpritesExtracted: Boolean = false
    var nameFile: String? = Utils.nameFile

    @JvmStatic
    fun start() {
        processConfiguration()
        if (nameFile != null) processNames(FileOperations.ReadFileText("./data/names.dat"))
        try {
            ExtractCacheFiles()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun ExtractCacheFiles() {
        Main.log(this.javaClass.name, "Extracting Cache Files...")
        for (i in Cache?.indices!!) {
            if (Utils.toExtract[i]) {
                if (Cache?.get(i) == null) {
                    Main.log(this.javaClass.name, "Cache is null skipped: index $i")
                    break
                }
                Main.log(
                    this.javaClass.name,
                    " - Extracting Expected " + Cache!![i]!!.numberOfFiles + " in Cache " + i + " \"" + CacheType!![i] + "\"..."
                )
                FileOperations.TotalWrite = 0
                for (j in 0 until Cache!![i]!!.numberOfFiles) {
                    val localByteStream = Cache!![i]!!.getFile(j)
                    if (localByteStream != null) {
                        val arrayOfByte: ByteArray
                        if (cacheVersion === "new") {
                            arrayOfByte = Unpack.unpackSecondMethod(localByteStream)
                            FileOperations.WriteFile(
                                OutputDir + CacheType!![i] + "/" + j + "." + CacheExt!![i]!!, arrayOfByte
                            )
                        } else {
                            arrayOfByte = Unpack.unpack(localByteStream, OutputDir + CacheType!![i] + "/" + j)
                            FileOperations.WriteFile(
                                OutputDir + CacheType!![i] + "/" + j + "." + CacheExt!![i], arrayOfByte
                            )
                        }
                    } else {
                        Main.log(this.javaClass.name, "Stream is null")
                    }
                }
                Main.log(
                    this.javaClass.name,
                    "  - " + FileOperations.TotalWrite + " Files Extracted to /" + CacheType!![i] + "/*." + CacheExt!![i] + "."
                )
                //break;
            }
        }
        // if (NameFile != null)
        // {
        //   System.out.Main.log(this.javaClass.name,"Now Checking to see if any Naming was not found");
        //   for (int i = 0; i < fFound.length; i++)
        //     if (fFound[i] == false)
        //       System.out.Main.log(this.javaClass.name,"Did not find " + fNames[i] + " filesize: " + fInts[i]);
        // }
        Main.log(this.javaClass.name, "Successfully extracted.")
    }

    @JvmStatic
    fun checkNames(paramInt: Int): String? {
        for (i in fInts.indices) {
            if (fInts[i] == paramInt) {
                fFound[i] = true
                return fNames[i]
            }
        }
        Main.log(this.javaClass.name, "Could Not Find Name for: $paramInt")
        return "$paramInt.DAT"
    }

    fun processNames(paramString: String) {
        Main.log(this.javaClass.name, "Parsing Names...")
        fNames = paramString.split("\r\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        fFound = BooleanArray(fNames.size)
        val arrayOfInt = IntArray(fNames.size)
        for (i in fNames.indices) {
            if (!fNames[i]!!.startsWith("#")) {
                val arrayOfString = fNames[i]!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                arrayOfInt[i] = arrayOfString[0].toInt()
                fNames[i] = arrayOfString[1]
            }
        }
        fInts = arrayOfInt
        Main.log(this.javaClass.name, " - " + fNames.size + " Names read.")
    }

    fun processConfiguration() {
        try {
            Main.log(this.javaClass.name, "Parsing configuration...")
            nameFile = Utils.nameFile
            cacheVersion = Utils.format
            InputDir = Utils.inputFolder
            OutputDir = Utils.outputFolder

            if (cacheVersion === "old") {
                Cache = arrayOfNulls(5)
                CacheType = arrayOfNulls(5)
                CacheExt = arrayOfNulls(5)
                CacheData = RandomAccessFile("$InputDir/main_file_cache.dat", "rw")
            } else {
                Cache = arrayOfNulls(Utils.toExtract.size)
                CacheType = arrayOfNulls(Utils.toExtract.size)
                CacheExt = arrayOfNulls(Utils.toExtract.size)
                CacheData = RandomAccessFile("$InputDir/main_file_cache.dat2", "rw")
            }

            for (i in Utils.toExtract.indices) {
                if (Utils.toExtract[i] == true) {
                    Cache!![i] = CacheFile(FileOperations.ReadFile("$InputDir/main_file_cache.idx$i")!!, CacheData!!)
                    CacheType!![i] = "/$i"
                    CacheExt!![i] = "dat"
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}