package uz.kuvondikov.clickup.service.base;

import lombok.RequiredArgsConstructor;
import uz.kuvondikov.clickup.mapper.base.BaseMapper;
import uz.kuvondikov.clickup.repository.base.BaseRepository;

@RequiredArgsConstructor
public class AbstractService<R extends BaseRepository, M extends BaseMapper> {
    protected final R repository;
    protected final M mapper;
}
