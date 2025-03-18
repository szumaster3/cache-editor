package com.alex.tools;

import com.alex.filestore.Archive;
import com.alex.filestore.ArchiveReference;
import com.alex.filestore.Index;
import com.alex.filestore.Store;

import java.io.IOException;
import java.util.Random;

public class ArchiveValidation {
    public static void main(String[] args) throws IOException {
        Store rscache = new Store("");

        for (int i = 0; i < rscache.getIndexes().length; ++i) {
            if (i != 5) {
                Index index = rscache.getIndexes()[i];
                System.out.println("checking index: " + i);
                int[] arr$ = index.getTable().getValidArchiveIds();
                int len$ = arr$.length;

                for (int i$ = 0; i$ < len$; ++i$) {
                    int archiveId = arr$[i$];
                    Archive archive = index.getArchive(archiveId);
                    if (archive == null) {
                        System.out.println("Missing:: " + i + ", " + archiveId);
                    } else {
                        ArchiveReference reference = index.getTable().getArchives()[archiveId];
                        if (archive.getRevision() != reference.revision) {
                            System.out.println("corrupted: " + i + ", " + archiveId);
                        }
                    }
                }
            }
        }

    }

    public static int[] generateKeys() {
        int[] keys = new int[4];

        for (int index = 0; index < keys.length; ++index) {
            keys[index] = (new Random()).nextInt();
        }

        return keys;
    }
}
