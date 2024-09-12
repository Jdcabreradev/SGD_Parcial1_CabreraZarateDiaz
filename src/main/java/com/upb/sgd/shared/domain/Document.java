package com.upb.sgd.shared.domain;

import java.util.Date;
import java.util.List;

public class Document extends Directory {
    public byte[] fileData;

    public Document(){
        this.dirType = DirType.FILE;
    }
}