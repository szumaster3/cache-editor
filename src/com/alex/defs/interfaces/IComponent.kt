package com.alex.defs.interfaces

import com.alex.io.InputStream
import java.util.*
import kotlin.experimental.inv

/**
 * The type Component.
 */
abstract class IComponent {
    /**
     * The An object array 2296.
     */
    lateinit var anObjectArray2296: Array<Any?>

    /**
     * The An int 2297.
     */
    var anInt2297: Int = 0

    /**
     * The An int 2298.
     */
    var anInt2298: Int = -1

    /**
     * The An int array 2299.
     */
    var anIntArray2299: IntArray? = null

    /**
     * The An int 2300.
     */
    var anInt2300: Int = 0

    /**
     * The An int 2301.
     */
    var anInt2301: Int = 1

    /**
     * The An object array 2302.
     */
    lateinit var anObjectArray2302: Array<Any?>

    /**
     * The An int 2303.
     */
    var anInt2303: Int = -1

    /**
     * The An int 2305.
     */
    var anInt2305: Int = 0

    /**
     * The A boolean 2306.
     */
    var aBoolean2306: Boolean = false

    /**
     * The An int 2308.
     */
    var anInt2308: Int = 0

    /**
     * The An int array 2310.
     */
    lateinit var anIntArray2310: IntArray

    /**
     * The A byte 2311.
     */
    var aByte2311: Byte = 0

    /**
     * The An int 2312.
     */
    var anInt2312: Int = 0

    /**
     * The An object array 2313.
     */
    lateinit var anObjectArray2313: Array<Any?>

    /**
     * The An int 2314.
     */
    var anInt2314: Int = 0

    /**
     * The An int array 2315.
     */
    lateinit var anIntArray2315: IntArray

    /**
     * The An object array 2316.
     */
    abstract var anObjectArray2316: Array<Any?>

    /**
     * The A byte array 2317.
     */
    lateinit var aByteArray2317: ByteArray

    /**
     * The An object array 2318.
     */
    abstract var anObjectArray2318: Array<Any?>

    /**
     * The An int 2319.
     */
    var anInt2319: Int = 0

    /**
     * The An int 2321.
     */
    var anInt2321: Int = -1

    /**
     * The Height.
     */
    var height: Int = 0

    /**
     * The An int array 2323.
     */
    abstract var anIntArray2323: IntArray

    /**
     * The An int 2324.
     */
    var anInt2324: Int = 0

    /**
     * The An int 2325.
     */
    var anInt2325: Int = 0

    /**
     * The A class 173 array 2326.
     */
    abstract var aClass173Array2326: Array<IComponent>

    /**
     * The An int array array 2327.
     */
    abstract var anIntArrayArray2327: Array<IntArray?>

    /**
     * The An object array 2328.
     */
    abstract var anObjectArray2328: Array<Any?>

    /**
     * The A string 2329.
     */
    var aString2329: String = "Ok"

    /**
     * The A string 2330.
     */
    var aString2330: String? = null

    /**
     * The An object array 2331.
     */
    abstract var anObjectArray2331: Array<Any?>

    /**
     * The An int 2332.
     */
    var anInt2332: Int = 0

    /**
     * The An int 2333.
     */
    var anInt2333: Int = 0

    /**
     * The A string 2334.
     */
    var aString2334: String = ""

    /**
     * The An int 2335.
     */
    var anInt2335: Int = 0

    /**
     * The An object array 2336.
     */
    abstract var anObjectArray2336: Array<Any>

    /**
     * The An int array 2337.
     */
    abstract var anIntArray2337: IntArray

    /**
     * The An int 2338.
     */
    var anInt2338: Int = 0

    /**
     * The An int 2340.
     */
    var anInt2340: Int = 0

    /**
     * The A byte 2341.
     */
    var aByte2341: Byte = 0

    /**
     * The A boolean 2342.
     */
    var aBoolean2342: Boolean = false

    /**
     * The An int 2343.
     */
    var anInt2343: Int = 0

    /**
     * The An object array 2344.
     */
    abstract var anObjectArray2344: Array<Any>

    /**
     * The A class 173 2345.
     */
    var aClass173_2345: IComponent? = null

    /**
     * The An int 2347.
     */
    var anInt2347: Int = 0

    /**
     * The An object array 2348.
     */
    abstract var anObjectArray2348: Array<Any>

    /**
     * The An int 2349.
     */
    var anInt2349: Int = -1

    /**
     * The An int 2350.
     */
    var anInt2350: Int = -1

    /**
     * The An object array 2351.
     */
    abstract var anObjectArray2351: Array<Any?>

    /**
     * The An object array 2352.
     */
    abstract var anObjectArray2352: Array<Any>

    /**
     * The A boolean 2353.
     */
    var aBoolean2353: Boolean = false

    /**
     * The Use scripts.
     */
    var useScripts: Boolean = false

    /**
     * The A byte 2356.
     */
    var aByte2356: Byte = 0

    /**
     * The A string 2357.
     */
    var aString2357: String = ""

    /**
     * The Model id.
     */
    var modelId: Int = 0

    /**
     * The An int array 2360.
     */
    abstract var anIntArray2360: IntArray?

    /**
     * The An int 2361.
     */
    var anInt2361: Int = -1

    /**
     * The An object array 2362.
     */
    abstract var anObjectArray2362: Array<Any>

    /**
     * The A string array 2363.
     */
    abstract var aStringArray2363: Array<String?>

    /**
     * The An int 2364.
     */
    var anInt2364: Int = 0

    /**
     * The An int 2365.
     */
    var anInt2365: Int = -1

    /**
     * The A boolean 2366.
     */
    var aBoolean2366: Boolean = false

    /**
     * The A boolean 2367.
     */
    var aBoolean2367: Boolean = false

    /**
     * The A boolean 2368.
     */
    var aBoolean2368: Boolean = false

    /**
     * The An int 2369.
     */
    var anInt2369: Int = 0

    /**
     * The An object array 2371.
     */
    abstract var anObjectArray2371: Array<Any?>

    /**
     * The A string 2373.
     */
    var aString2373: String = ""

    /**
     * The An int 2374.
     */
    var anInt2374: Int = -1

    /**
     * The An int 2375.
     */
    var anInt2375: Int = -1

    /**
     * The Image id.
     */
    var imageId: Int = -1

    /**
     * The An int array 2379.
     */
    abstract var anIntArray2379: IntArray

    /**
     * The A boolean 2380.
     */
    var aBoolean2380: Boolean = false

    /**
     * The An int 2381.
     */
    var anInt2381: Int = 0

    /**
     * The An int 2382.
     */
    var anInt2382: Int = 0

    /**
     * The A short 2383.
     */
    var aShort2383: Short = 0

    /**
     * The An int array 2384.
     */
    abstract var anIntArray2384: IntArray

    /**
     * The A string array 2385.
     */
    abstract var aStringArray2385: Array<String?>

    /**
     * The An int 2386.
     */
    var anInt2386: Int = -1

    /**
     * The An int array 2388.
     */
    abstract var anIntArray2388: IntArray?

    /**
     * The An int 2389.
     */
    var anInt2389: Int = 0

    /**
     * The An int 2390.
     */
    var anInt2390: Int = 0

    /**
     * The A string 2391.
     */
    var aString2391: String = ""

    /**
     * The A boolean 2393.
     */
    var aBoolean2393: Boolean = false

    /**
     * The An int 2394.
     */
    var anInt2394: Int = 1

    /**
     * The An object array 2395.
     */
    abstract var anObjectArray2395: Array<Any?>

    /**
     * The An int 2396.
     */
    var anInt2396: Int = 0

    /**
     * The An int 2397.
     */
    var anInt2397: Int = 0

    /**
     * The Settings.
     */
    var settings: IComponentSettings? = null

    /**
     * The An object array 2399.
     */
    abstract var anObjectArray2399: Array<Any>

    /**
     * The An int array 2400.
     */
    abstract var anIntArray2400: IntArray

    /**
     * The A boolean 2401.
     */
    var aBoolean2401: Boolean = false

    /**
     * The An object array 2402.
     */
    abstract var anObjectArray2402: Array<Any?>

    /**
     * The An int 2403.
     */
    var anInt2403: Int = 100

    /**
     * The Hidden.
     */
    var hidden: Boolean = false

    /**
     * The An object array 2405.
     */
    abstract var anObjectArray2405: Array<Any?>

    /**
     * The An int array 2407.
     */
    abstract var anIntArray2407: IntArray

    /**
     * The An object array 2408.
     */
    abstract var anObjectArray2408: Array<Any?>

    /**
     * The An int 2409.
     */
    var anInt2409: Int = 0

    /**
     * The An object array 2410.
     */
    abstract var anObjectArray2410: Array<Any?>

    /**
     * The An int 2411.
     */
    var anInt2411: Int = 0

    /**
     * The An int 2412.
     */
    var anInt2412: Int = 0

    /**
     * The A boolean 2413.
     */
    var aBoolean2413: Boolean = false

    /**
     * The An int 2414.
     */
    var anInt2414: Int = 0

    /**
     * The An int 2415.
     */
    var anInt2415: Int = 0

    /**
     * The Model type.
     */
    var modelType: Int = 1

    /**
     * The A byte array 2417.
     */
    abstract var aByteArray2417: ByteArray

    /**
     * The An int array 2418.
     */
    abstract var anIntArray2418: IntArray

    /**
     * The A boolean 2419.
     */
    var aBoolean2419: Boolean = false

    /**
     * The A short 2420.
     */
    var aShort2420: Short = 3000

    /**
     * The An int 2421.
     */
    var anInt2421: Int = -1

    /**
     * The A boolean 2422.
     */
    var aBoolean2422: Boolean = false

    /**
     * The An int 2423.
     */
    var anInt2423: Int = 0

    /**
     * The An int 2424.
     */
    var anInt2424: Int = 0

    /**
     * The Default script.
     */
    abstract var defaultScript: Array<Any?>

    /**
     * The An int 2427.
     */
    var anInt2427: Int = 0

    /**
     * The A boolean 2429.
     */
    var aBoolean2429: Boolean = false

    /**
     * The An int array 2431.
     */
    abstract var anIntArray2431: IntArray

    /**
     * The Y.
     */
    var y: Int = 0

    /**
     * The Border thickness.
     */
    var borderThickness: Int = 0

    /**
     * The A boolean 2434.
     */
    var aBoolean2434: Boolean = false

    /**
     * The An int 2435.
     */
    var anInt2435: Int = 0

    /**
     * The A boolean 2436.
     */
    var aBoolean2436: Boolean = false

    /**
     * The An int 2437.
     */
    var anInt2437: Int = 0

    /**
     * The An int 2438.
     */
    var anInt2438: Int = 1

    /**
     * The An object array 2439.
     */
    abstract var anObjectArray2439: Array<Any?>

    /**
     * The Width.
     */
    var width: Int = 0

    /**
     * The An int 2441.
     */
    var anInt2441: Int = 0

    /**
     * The An int 2442.
     */
    var anInt2442: Int = 0

    /**
     * The An int 2443.
     */
    var anInt2443: Int = -1

    /**
     * The An int 2444.
     */
    var anInt2444: Int = 0

    /**
     * The X.
     */
    var x: Int = 0

    /**
     * The An object array 2446.
     */
    abstract var anObjectArray2446: Array<Any?>

    /**
     * The An object array 2447.
     */
    abstract var anObjectArray2447: Array<Any>

    /**
     * The An int 2448.
     */
    var anInt2448: Int = -1

    /**
     * The An int array 2449.
     */
    abstract var anIntArray2449: IntArray

    /**
     * The An int 2450.
     */
    var anInt2450: Int = 0

    /**
     * The An int 2451.
     */
    var anInt2451: Int = 0

    /**
     * The An int array 2452.
     */
    abstract var anIntArray2452: IntArray?

    /**
     * The An int 2453.
     */
    var anInt2453: Int = -1

    /**
     * The An object array 2454.
     */
    abstract var anObjectArray2454: Array<Any?>

    /**
     * The Hash.
     */
    var hash: Int = -1

    /**
     * The Parent id.
     */
    var parentId: Int = -1

    /**
     * The An int 2457.
     */
    var anInt2457: Int = -1

    /**
     * The An int 2458.
     */
    var anInt2458: Int = 0

    /**
     * The An int 2459.
     */
    var anInt2459: Int = 0

    /**
     * The An int 2461.
     */
    var anInt2461: Int = 0

    /**
     * The An object array 2462.
     */
    abstract var anObjectArray2462: Array<Any?>

    /**
     * The A string 2463.
     */
    var aString2463: String = ""

    /**
     * The An object array 2464.
     */
    abstract var anObjectArray2464: Array<Any>

    /**
     * The An object array 2465.
     */
    abstract var anObjectArray2465: Array<Any?>

    /**
     * The An int 2467.
     */
    var anInt2467: Int = 0

    /**
     * The A byte 2469.
     */
    var aByte2469: Byte = 0

    /**
     * The Type.
     */
    var type: Int = 0

    /**
     * The An int 2471.
     */
    var anInt2471: Int = 1

    /**
     * The An int array 2472.
     */
    abstract var anIntArray2472: IntArray?

    /**
     * The A string 2473.
     */
    var aString2473: String? = null

    /**
     * The An int 2474.
     */
    var anInt2474: Int = 2

    /**
     * The An object array 2475.
     */
    abstract var anObjectArray2475: Array<Any>

    /**
     * The A boolean 2476.
     */
    var aBoolean2476: Boolean = false

    /**
     * The An int 2477.
     */
    var anInt2477: Int = 0

    /**
     * The An int array 2478.
     */
    abstract var anIntArray2478: IntArray

    /**
     * The An int 2479.
     */
    var anInt2479: Int = 0

    /**
     * The An int 2480.
     */
    var anInt2480: Int = 0

    /**
     * The An int 2481.
     */
    var anInt2481: Int = 1

    /**
     * The An int 2482.
     */
    var anInt2482: Int = 0

    /**
     * The An object array 2483.
     */
    abstract var anObjectArray2483: Array<Any>

    /**
     * The An int 2484.
     */
    var anInt2484: Int = 0

    /**
     * The A boolean 4782.
     */
    var aBoolean4782: Boolean = false

    /**
     * Decode scripts format.
     *
     * @param stream the stream
     */
    fun decodeScriptsFormat(stream: InputStream) {
        this.useScripts = true
        var newInt = stream.readUnsignedByte()
        if (newInt == 255) {
            newInt = -1
        }

        this.type = stream.readUnsignedByte()
        if ((this.type and 128).inv() != -1) {
            this.type = this.type and 127
            this.aString2473 = stream.readString()
        }

        this.anInt2441 = stream.readUnsignedShort()
        this.x = stream.readShort()
        this.y = stream.readShort()
        this.width = stream.readUnsignedShort()
        this.height = stream.readUnsignedShort()
        this.aByte2356 = stream.readByte().toByte()
        this.aByte2341 = stream.readByte().toByte()
        this.aByte2469 = stream.readByte().toByte()
        this.aByte2311 = stream.readByte().toByte()
        this.parentId = stream.readUnsignedShort()
        if (parentId.inv() != -65536) {
            this.parentId += this.hash and -65536
        } else {
            this.parentId = -1
        }

        val i_17_ = stream.readUnsignedByte()
        this.hidden = (1 and i_17_).inv() != -1
        if (newInt >= 0) {
            this.aBoolean2429 = (i_17_ and 2).inv() != -1
        }

        if (type.inv() == -1) {
            this.anInt2444 = stream.readUnsignedShort()
            this.anInt2479 = stream.readUnsignedShort()
            if (newInt.inv() > -1) {
                this.aBoolean2429 = stream.readUnsignedByte() == 1
            }
        }

        var settingsHash: Int
        if (type.inv() == -6) {
            this.imageId = stream.readInt()
            this.anInt2381 = stream.readUnsignedShort()
            settingsHash = stream.readUnsignedByte()
            this.aBoolean2422 = (2 and settingsHash).inv() != -1
            this.aBoolean2434 = (settingsHash and 1).inv() != -1
            this.anInt2369 = stream.readUnsignedByte()
            this.borderThickness = stream.readUnsignedByte()
            this.anInt2325 = stream.readInt()
            this.aBoolean2419 = stream.readUnsignedByte().inv() == -2
            this.aBoolean2342 = stream.readUnsignedByte().inv() == -2
            this.anInt2467 = stream.readInt()
            if (newInt.inv() <= -4) {
                this.aBoolean4782 = stream.readUnsignedByte().inv() == -2
            }
        }

        if (type.inv() == -7) {
            this.modelType = 1
            this.modelId = stream.readBigSmart()
            this.anInt2480 = stream.readShort()
            this.anInt2459 = stream.readShort()
            this.anInt2461 = stream.readUnsignedShort()
            this.anInt2482 = stream.readUnsignedShort()
            this.anInt2308 = stream.readUnsignedShort()
            this.anInt2403 = stream.readUnsignedShort()
            this.anInt2443 = stream.readUnsignedShort()
            if (this.anInt2443 == '\uffff'.code) {
                this.anInt2443 = -1
            }

            this.aBoolean2476 = stream.readUnsignedByte() == 1
            this.aShort2383 = stream.readUnsignedShort().toShort()
            this.aShort2420 = stream.readUnsignedShort().toShort()
            this.aBoolean2368 = stream.readUnsignedByte() == 1
            if (aByte2356.inv().toInt() != -1) {
                this.anInt2423 = stream.readUnsignedShort()
            }

            if (aByte2341.toInt() != 0) {
                this.anInt2397 = stream.readUnsignedShort()
            }
        }

        if (this.type == 4) {
            this.anInt2375 = stream.readBigSmart()
            if (anInt2375.inv() == -65536) {
                this.anInt2375 = -1
            }

            this.aString2357 = stream.readString()
            if (aString2357.lowercase(Locale.getDefault()).contains("ship")) {
                println(this.hash shr 16)
            }

            this.anInt2364 = stream.readUnsignedByte()
            this.anInt2312 = stream.readUnsignedByte()
            this.anInt2297 = stream.readUnsignedByte()
            this.aBoolean2366 = stream.readUnsignedByte().inv() == -2
            this.anInt2467 = stream.readInt()
        }

        if (this.type == 3) {
            this.anInt2467 = stream.readInt()
            this.aBoolean2367 = stream.readUnsignedByte().inv() == -2
            this.anInt2369 = stream.readUnsignedByte()
        }

        if (type.inv() == -10) {
            this.anInt2471 = stream.readUnsignedByte()
            this.anInt2467 = stream.readInt()
            this.aBoolean2306 = stream.readUnsignedByte().inv() == -2
        }

        settingsHash = stream.read24BitInt()
        var i_28_ = stream.readUnsignedByte()
        var i_30_: Int
        if (i_28_ != 0) {
            this.anIntArray2449 = IntArray(11)
            this.aByteArray2417 = ByteArray(11)

            this.aByteArray2317 = ByteArray(11)
            while (i_28_.inv() != -1) {
                i_30_ = -1 + (i_28_ shr 360744868)
                i_28_ = i_28_ shl -456693784 or stream.readUnsignedByte()
                i_28_ = i_28_ and 4095
                if (i_28_.inv() != -4096) {
                    anIntArray2449[i_30_] = i_28_
                } else {
                    anIntArray2449[i_30_] = -1
                }

                aByteArray2317[i_30_] = stream.readByte().toByte()
                if (aByteArray2317[i_30_].inv().toInt() != -1) {
                    this.aBoolean2401 = true
                }

                aByteArray2417[i_30_] = stream.readByte().toByte()
                i_28_ = stream.readUnsignedByte()
            }
        }

        this.aString2391 = stream.readString()
        i_30_ = stream.readUnsignedByte()
        val i_31_ = i_30_ and 15
        var i_33_: Int
        if (i_31_.inv() < -1) {
            this.aStringArray2385 = arrayOfNulls(i_31_)

            i_33_ = 0
            while (i_31_ > i_33_) {
                aStringArray2385[i_33_] = stream.readString()
                ++i_33_
            }
        }

        i_33_ = i_30_ shr -686838332
        var defaultHash: Int
        var i: Int
        if (i_33_.inv() < -1) {
            defaultHash = stream.readUnsignedByte()
            this.anIntArray2315 = IntArray(1 + defaultHash)

            i = 0
            while (i < anIntArray2315.size) {
                anIntArray2315[i] = -1
                ++i
            }

            anIntArray2315[defaultHash] = stream.readUnsignedShort()
        }

        if (i_33_.inv() < -2) {
            defaultHash = stream.readUnsignedByte()
            anIntArray2315[defaultHash] = stream.readUnsignedShort()
        }

        this.aString2330 = stream.readString()
        if (this.aString2330 == "") {
            this.aString2330 = null
        }

        this.anInt2335 = stream.readUnsignedByte()
        this.anInt2319 = stream.readUnsignedByte()
        this.aBoolean2436 = stream.readUnsignedByte().inv() == -2
        this.aString2463 = stream.readString()
        defaultHash = -1
        if (method2412(settingsHash).inv() != -1) {
            defaultHash = stream.readUnsignedShort()
            if (defaultHash.inv() == -65536) {
                defaultHash = -1
            }

            this.anInt2303 = stream.readUnsignedShort()
            if (this.anInt2303 == '\uffff'.code) {
                this.anInt2303 = -1
            }

            this.anInt2374 = stream.readUnsignedShort()
            if (this.anInt2374 == '\uffff'.code) {
                this.anInt2374 = -1
            }
        }

        this.settings = IComponentSettings(settingsHash, defaultHash)
        this.defaultScript = this.decodeScript(stream)
        this.anObjectArray2462 = this.decodeScript(stream)
        this.anObjectArray2402 = this.decodeScript(stream)
        this.anObjectArray2371 = this.decodeScript(stream)
        this.anObjectArray2408 = this.decodeScript(stream)
        this.anObjectArray2439 = this.decodeScript(stream)
        this.anObjectArray2454 = this.decodeScript(stream)
        this.anObjectArray2410 = this.decodeScript(stream)
        this.anObjectArray2316 = this.decodeScript(stream)
        this.anObjectArray2465 = this.decodeScript(stream)
        this.anObjectArray2446 = this.decodeScript(stream)
        this.anObjectArray2313 = this.decodeScript(stream)
        this.anObjectArray2318 = this.decodeScript(stream)
        this.anObjectArray2328 = this.decodeScript(stream)
        this.anObjectArray2395 = this.decodeScript(stream)
        this.anObjectArray2331 = this.decodeScript(stream)
        this.anObjectArray2405 = this.decodeScript(stream)
        this.anObjectArray2351 = this.decodeScript(stream)
        this.anObjectArray2302 = this.decodeScript(stream)
        this.anObjectArray2296 = this.decodeScript(stream)
        this.anIntArray2452 = this.method2465(stream)
        this.anIntArray2472 = this.method2465(stream)
        this.anIntArray2360 = this.method2465(stream)
        this.anIntArray2388 = this.method2465(stream)
        this.anIntArray2299 = this.method2465(stream)
        println("useScripts = " + this.useScripts)
        println("x = " + this.x)
        println("y = " + this.y)
        println("width = " + this.width)
        println("height = " + this.height)
        println("parentId = " + this.parentId)
        println("imageId = " + this.imageId)
        println("modelId = " + this.modelId)
        println("aString2357 = " + this.aString2357)
        println("aString2391 = " + this.aString2391)

        i = 0
        while (i < aStringArray2385.size) {
            println("aStringArray2385[" + i + "] = " + aStringArray2385[i])
            ++i
        }

        i = 0
        while (i < anIntArray2315.size) {
            println("anIntArray2315[" + i + "] = " + anIntArray2315[i])
            ++i
        }

        println("aString2330 = " + this.aString2330)
        println("aBoolean2436 = " + this.aBoolean2436)
        println("aString2463 = " + this.aString2463)
        println("anInt2303 = " + this.anInt2303)
        println("anInt2364 = " + this.anInt2364)

        i = 0
        while (i < anObjectArray2462.size) {
            println("anObjectArray2462[" + i + "] = " + anObjectArray2462[i])
            ++i
        }

        i = 0
        while (i < anObjectArray2402.size) {
            println("anObjectArray2402[" + i + "] = " + anObjectArray2402[i])
            ++i
        }

        i = 0
        while (i < anObjectArray2371.size) {
            println("anObjectArray2371[" + i + "] = " + anObjectArray2371[i])
            ++i
        }

        i = 0
        while (i < anObjectArray2408.size) {
            println("anObjectArray2408[" + i + "] = " + anObjectArray2408[i])
            ++i
        }

        i = 0
        while (i < anObjectArray2439.size) {
            println("anObjectArray2439[" + i + "] = " + anObjectArray2439[i])
            ++i
        }

        i = 0
        while (i < anObjectArray2454.size) {
            println("anObjectArray2454[" + i + "] = " + anObjectArray2454[i])
            ++i
        }

        i = 0
        while (i < anObjectArray2410.size) {
            println("anObjectArray2410[" + i + "] = " + anObjectArray2410[i])
            ++i
        }

        i = 0
        while (i < anObjectArray2316.size) {
            println("anObjectArray2316[" + i + "] = " + anObjectArray2316[i])
            ++i
        }

        i = 0
        while (i < anObjectArray2465.size) {
            println("anObjectArray2465[" + i + "] = " + anObjectArray2465[i])
            ++i
        }

        i = 0
        while (i < anObjectArray2446.size) {
            println("anObjectArray2446[" + i + "] = " + anObjectArray2446[i])
            ++i
        }

        i = 0
        while (i < anObjectArray2313.size) {
            println("anObjectArray2313[" + i + "] = " + anObjectArray2313[i])
            ++i
        }

        i = 0
        while (i < anObjectArray2318.size) {
            println("anObjectArray2318[" + i + "] = " + anObjectArray2318[i])
            ++i
        }

        i = 0
        while (i < anObjectArray2328.size) {
            println("anObjectArray2328[" + i + "] = " + anObjectArray2328[i])
            ++i
        }

        i = 0
        while (i < anObjectArray2395.size) {
            println("anObjectArray2395[" + i + "] = " + anObjectArray2395[i])
            ++i
        }

        i = 0
        while (i < anObjectArray2331.size) {
            println("anObjectArray2331[" + i + "] = " + anObjectArray2331[i])
            ++i
        }

        i = 0
        while (i < anObjectArray2405.size) {
            println("anObjectArray2405[" + i + "] = " + anObjectArray2405[i])
            ++i
        }

        i = 0
        while (i < anObjectArray2351.size) {
            println("anObjectArray2351[" + i + "] = " + anObjectArray2351[i])
            ++i
        }

        i = 0
        while (i < anObjectArray2302.size) {
            println("anObjectArray2302[" + i + "] = " + anObjectArray2302[i])
            ++i
        }

        i = 0
        while (i < anObjectArray2296.size) {
            println("anObjectArray2296[" + i + "] = " + anObjectArray2296[i])
            ++i
        }

        i = 0
        while (i < anIntArray2452!!.size) {
            println("anIntArray2452[" + i + "] = " + anIntArray2452!![i])
            ++i
        }

        i = 0
        while (i < anIntArray2472!!.size) {
            println("anIntArray2472[" + i + "] = " + anIntArray2472!![i])
            ++i
        }

        i = 0
        while (i < anIntArray2360!!.size) {
            println("anIntArray2360[" + i + "] = " + anIntArray2360!![i])
            ++i
        }

        i = 0
        while (i < anIntArray2388!!.size) {
            println("anIntArray2388[" + i + "] = " + anIntArray2388!![i])
            ++i
        }

        i = 0
        while (i < anIntArray2299!!.size) {
            println("anIntArray2299[" + i + "] = " + anIntArray2299!![i])
            ++i
        }
    }

    /**
     * Decode script object [ ].
     *
     * @param stream the stream
     * @return the object [ ]
     */
    fun decodeScript(stream: InputStream): Array<Any?> {
        val size = stream.readUnsignedByte()
        val objects = arrayOfNulls<Any>(size)

        var index = 0
        while (size.inv() < index.inv()) {
            val i_41_ = stream.readUnsignedByte()
            if (i_41_.inv() == -1) {
                objects[index] = stream.readInt()
            } else if (i_41_ == 1) {
                objects[index] = stream.readString()
            }
            ++index
        }

        this.aBoolean2353 = true
        return objects
    }

    /**
     * Method 2465 int [ ].
     *
     * @param stream the stream
     * @return the int [ ]
     */
    fun method2465(stream: InputStream): IntArray? {
        val size = stream.readUnsignedByte()
        if (size == 0) {
            return null
        } else {
            val array = IntArray(size)

            var index = 0
            while (size > index) {
                array[index] = stream.readInt()
                ++index
            }

            return array
        }
    }

    /**
     * Decode noscripts format.
     *
     * @param stream the stream
     */
    fun decodeNoscriptsFormat(stream: InputStream) {
        this.useScripts = false
        this.type = stream.readUnsignedByte()
        this.anInt2324 = stream.readUnsignedByte()
        this.anInt2441 = stream.readUnsignedShort()
        this.x = stream.readShort()
        this.y = stream.readShort()
        this.width = stream.readUnsignedShort()
        this.height = stream.readUnsignedShort()
        this.aByte2341 = 0
        this.aByte2356 = 0
        this.aByte2311 = 0
        this.aByte2469 = 0
        this.anInt2369 = stream.readUnsignedByte()
        this.parentId = stream.readUnsignedShort()
        if (parentId.inv() == -65536) {
            this.parentId = -1
        } else {
            this.parentId += this.hash and -65536
        }

        this.anInt2448 = stream.readUnsignedShort()
        if (anInt2448.inv() == -65536) {
            this.anInt2448 = -1
        }

        var i = stream.readUnsignedByte()
        var i_1_: Int
        if (i.inv() < -1) {
            this.anIntArray2407 = IntArray(i)
            this.anIntArray2384 = IntArray(i)

            i_1_ = 0
            while (i > i_1_) {
                anIntArray2384[i_1_] = stream.readUnsignedByte()
                anIntArray2407[i_1_] = stream.readUnsignedShort()
                ++i_1_
            }
        }

        i_1_ = stream.readUnsignedByte()
        var i_5_: Int
        var `is`: Int
        var i_13_: Int
        if (i_1_.inv() < -1) {
            this.anIntArrayArray2327 = arrayOfNulls(i_1_)

            i_5_ = 0
            while (i_1_.inv() < i_5_.inv()) {
                `is` = stream.readUnsignedShort()
                anIntArrayArray2327[i_5_] = IntArray(`is`)

                i_13_ = 0
                while (`is`.inv() < i_13_.inv()) {
                    anIntArrayArray2327[i_5_]!![i_13_] = stream.readUnsignedShort()
                    if (anIntArrayArray2327[i_5_]!![i_13_].inv() == -65536) {
                        anIntArrayArray2327[i_5_]!![i_13_] = -1
                    }
                    ++i_13_
                }
                ++i_5_
            }
        }

        if (type.inv() == -1) {
            this.anInt2479 = stream.readUnsignedShort()
            this.hidden = stream.readUnsignedByte() == 1
        }

        if (this.type == 1) {
            stream.readUnsignedShort()
            stream.readUnsignedByte()
        }

        i_5_ = 0
        if (type.inv() == -3) {
            this.anIntArray2400 = IntArray(this.height * this.width)
            this.aByte2341 = 3
            this.anIntArray2418 = IntArray(this.height * this.width)
            this.aByte2356 = 3
            `is` = stream.readUnsignedByte()
            if (`is` == 1) {
                i_5_ = i_5_ or 268435456
            }

            i_13_ = stream.readUnsignedByte()
            if (i_13_ == 1) {
                i_5_ = i_5_ or 1073741824
            }

            val var10 = stream.readUnsignedByte()
            stream.readUnsignedByte()
            if (var10.inv() == -2) {
                i_5_ = i_5_ or Int.MIN_VALUE
            }

            this.anInt2332 = stream.readUnsignedByte()
            this.anInt2414 = stream.readUnsignedByte()
            this.anIntArray2337 = IntArray(20)
            this.anIntArray2323 = IntArray(20)
            this.anIntArray2431 = IntArray(20)
            var i_11_ = 0
            while (i_11_ < 20) {
                val var12 = stream.readUnsignedByte()
                if (var12.inv() != -2) {
                    anIntArray2431[i_11_] = -1
                } else {
                    anIntArray2323[i_11_] = stream.readShort()
                    anIntArray2337[i_11_] = stream.readShort()
                    anIntArray2431[i_11_] = stream.readInt()
                }
                ++i_11_
            }

            this.aStringArray2363 = arrayOfNulls(5)

            i_11_ = 0
            while (i_11_ < 5) {
                val var121 = stream.readString()
                if (var121.length.inv() < -1) {
                    aStringArray2363[i_11_] = var121
                    i_5_ = i_5_ or (1 shl 23 + i_11_)
                }
                ++i_11_
            }
        }

        if (type.inv() == -4) {
            this.aBoolean2367 = stream.readUnsignedByte().inv() == -2
        }

        if (type.inv() == -5 || this.type == 1) {
            this.anInt2312 = stream.readUnsignedByte()
            this.anInt2297 = stream.readUnsignedByte()
            this.anInt2364 = stream.readUnsignedByte()
            this.anInt2375 = stream.readUnsignedShort()
            if (anInt2375.inv() == -65536) {
                this.anInt2375 = -1
            }

            this.aBoolean2366 = stream.readUnsignedByte() == 1
        }

        if (type.inv() == -5) {
            this.aString2357 = stream.readString()
            this.aString2334 = stream.readString()
        }

        if (this.type == 1 || type.inv() == -4 || this.type == 4) {
            this.anInt2467 = stream.readInt()
        }

        if (this.type == 3 || this.type == 4) {
            this.anInt2424 = stream.readInt()
            this.anInt2451 = stream.readInt()
            this.anInt2477 = stream.readInt()
        }

        if (type.inv() == -6) {
            this.imageId = stream.readInt()
            this.anInt2349 = stream.readInt()
        }

        if (type.inv() == -7) {
            this.modelType = 1
            this.modelId = stream.readUnsignedShort()
            this.anInt2301 = 1
            if (this.modelId == '\uffff'.code) {
                this.modelId = -1
            }

            this.anInt2386 = stream.readUnsignedShort()
            if (anInt2386.inv() == -65536) {
                this.anInt2386 = -1
            }

            this.anInt2443 = stream.readUnsignedShort()
            if (this.anInt2443 == '\uffff'.code) {
                this.anInt2443 = -1
            }

            this.anInt2298 = stream.readUnsignedShort()
            if (this.anInt2298 == '\uffff'.code) {
                this.anInt2298 = -1
            }

            this.anInt2403 = stream.readUnsignedShort()
            this.anInt2461 = stream.readUnsignedShort()
            this.anInt2482 = stream.readUnsignedShort()
        }

        if (type.inv() == -8) {
            this.aByte2341 = 3
            this.anIntArray2418 = IntArray(this.width * this.height)
            this.aByte2356 = 3
            this.anIntArray2400 = IntArray(this.width * this.height)
            this.anInt2312 = stream.readUnsignedByte()
            this.anInt2375 = stream.readUnsignedShort()
            if (this.anInt2375 == '\uffff'.code) {
                this.anInt2375 = -1
            }

            this.aBoolean2366 = stream.readUnsignedByte() == 1
            this.anInt2467 = stream.readInt()
            this.anInt2332 = stream.readShort()
            this.anInt2414 = stream.readShort()
            `is` = stream.readUnsignedByte()
            if (`is`.inv() == -2) {
                i_5_ = i_5_ or 1073741824
            }

            this.aStringArray2363 = arrayOfNulls(5)

            i_13_ = 0
            while (i_13_ < 5) {
                val var101 = stream.readString()
                if (var101.length > 0) {
                    aStringArray2363[i_13_] = var101
                    i_5_ = i_5_ or (1 shl i_13_ + 23)
                }
                ++i_13_
            }
        }

        if (type.inv() == -9) {
            this.aString2357 = stream.readString()
        }

        if (this.anInt2324 == 2 || type.inv() == -3) {
            this.aString2463 = stream.readString()
            this.aString2373 = stream.readString()
            `is` = 63 and stream.readUnsignedShort()
            i_5_ = i_5_ or (`is` shl -116905845)
        }

        if (anInt2324.inv() == -2 || anInt2324.inv() == -5 || this.anInt2324 == 5 || this.anInt2324 == 6) {
            this.aString2329 = stream.readString()
            if (aString2329.length.inv() == -1) {
                if (anInt2324.inv() == -2) {
                    this.aString2329 = "Ok"
                }

                if (anInt2324.inv() == -5) {
                    this.aString2329 = "Select"
                }

                if (anInt2324.inv() == -6) {
                    this.aString2329 = "Select"
                }

                if (anInt2324.inv() == -7) {
                    this.aString2329 = "Continue"
                }
            }
        }

        if (this.anInt2324 == 1 || this.anInt2324 == 4 || anInt2324.inv() == -6) {
            i_5_ = i_5_ or 4194304
        }

        if (anInt2324.inv() == -7) {
            i_5_ = i_5_ or 1
        }

        this.settings = IComponentSettings(i_5_, -1)
        println("useScripts = " + this.useScripts)
        println("type = " + this.type)
        println("anInt2324 = " + this.anInt2324)
        println("anInt2441 = " + this.anInt2441)
        println("x = " + this.x)
        println("y = " + this.y)
        println("width = " + this.width)
        println("height = " + this.height)
        println("anInt2369 = " + this.anInt2369)
        println("parentId = " + this.parentId)
        println("anInt2448 = " + this.anInt2448)
        var var11: Byte = 0
        while (var11 < anIntArray2407.size) {
            println("anIntArray2407[" + var11 + "] = " + anIntArray2407[var11.toInt()])
            ++i
        }

        var11 = 0
        while (var11 < aStringArray2363.size) {
            println("aStringArray2363[" + var11 + "] = " + aStringArray2363[var11.toInt()])
            ++i
        }
    }

    companion object {
        /**
         * The constant anInt2346.
         */
        var anInt2346: Int = 0

        /**
         * Method 2412 int.
         *
         * @param arg0 the arg 0
         * @return the int
         */
        fun method2412(arg0: Int): Int {
            return 127 and (arg0 shr -809958741)
        }
    }
}
