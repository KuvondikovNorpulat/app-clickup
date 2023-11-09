package uz.kuvondikov.clickup.controller.base;

import lombok.RequiredArgsConstructor;
import uz.kuvondikov.clickup.service.base.BaseService;

@RequiredArgsConstructor
public abstract class AbstractController<S extends BaseService> implements BaseController {
    protected static final String API = "/api";
    protected static final String VERSION = "/v1";
    public static final String PATH = API + VERSION;
    public final S service;
}
