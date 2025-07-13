package com.editor.cache.iface.util

import com.alex.defs.interfaces.ComponentDefinition
import com.alex.filestore.Cache

object InterfacePacker {
    private const val INTERFACE_ARCHIVE: Int = 3

    /**
     * Packs a single component into the specified cache at the given interface and file ID.
     *
     * @param cache The cache to write the component to.
     * @param interfaceId The ID of the interface where the component belongs.
     * @param fileId The file ID where the component should be stored.
     * @param component The component definition to be packed.
     */
    @JvmStatic
    fun packComponent(
        cache: Cache,
        interfaceId: Int,
        fileId: Int,
        component: ComponentDefinition,
    ) {
        cache.indexes[INTERFACE_ARCHIVE].putFile(interfaceId, fileId, component.encode())
    }

    /**
     * Packs an entire interface (multiple components) into the specified cache.
     *
     * @param cache The cache to write the interface to.
     * @param interfaceId The ID of the interface to which the components belong.
     * @param components An array of components to be packed into the interface.
     */
    @JvmStatic
    fun packInterface(
        cache: Cache,
        interfaceId: Int,
        components: Array<ComponentDefinition?>?,
    ) {
        if (components == null) {
            println("No components to pack for interface $interfaceId")
            return
        }

        components.forEachIndexed { fileId, component ->
            if (component != null) {
                packComponent(cache, interfaceId, fileId, component)
                println("Packed component $fileId for interface $interfaceId")
            } else {
                println("Component at index $fileId is null, skipping.")
            }
        }
    }
}
