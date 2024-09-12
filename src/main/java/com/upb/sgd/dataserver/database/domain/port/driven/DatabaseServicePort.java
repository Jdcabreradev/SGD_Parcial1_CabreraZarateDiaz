package com.upb.sgd.dataserver.database.domain.port.driven;

import com.upb.sgd.shared.domain.DirType;
import com.upb.sgd.shared.domain.Directory;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DatabaseServicePort {
    List<Directory> findDirByParent(Integer parentId);
}
