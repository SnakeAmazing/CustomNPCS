package me.snakeamazing.customnpcs.module;

import me.snakeamazing.customnpcs.service.CustomNPCSService;
import me.snakeamazing.customnpcs.service.Service;
import me.yushust.inject.Binder;
import me.yushust.inject.Module;

public class ServiceModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(Service.class).to(CustomNPCSService.class).singleton();
    }
}
