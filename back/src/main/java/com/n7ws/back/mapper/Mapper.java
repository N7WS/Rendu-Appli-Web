package com.n7ws.back.mapper;

public interface Mapper {
    <Domain, Entity> Domain toDomain(Entity entity);
    <Model, Domain> Model toModel(Domain domain);
    <Entity, Model> Entity toEntity(Model model);
}
