package com.sunnao.aibox.module.infra.convert.file;

import com.sunnao.aibox.module.infra.controller.admin.file.vo.config.FileConfigSaveReqVO;
import com.sunnao.aibox.module.infra.dal.dataobject.file.FileConfigDO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-28T12:05:06+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
public class FileConfigConvertImpl implements FileConfigConvert {

    @Override
    public FileConfigDO convert(FileConfigSaveReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        FileConfigDO.FileConfigDOBuilder fileConfigDO = FileConfigDO.builder();

        fileConfigDO.id( bean.getId() );
        fileConfigDO.name( bean.getName() );
        fileConfigDO.storage( bean.getStorage() );
        fileConfigDO.remark( bean.getRemark() );

        return fileConfigDO.build();
    }
}
