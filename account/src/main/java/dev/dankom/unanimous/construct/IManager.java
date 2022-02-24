package dev.dankom.unanimous.construct;

import dev.dankom.unanimous.type.Savable;

import java.util.List;

public interface IManager extends Savable {
    List<? extends IGroup> getGroups();
}
