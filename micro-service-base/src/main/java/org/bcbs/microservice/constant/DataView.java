package org.bcbs.microservice.constant;

public final class DataView {

    private DataView() {
    }

    public interface BasicView {
    }

    public interface TypicalView extends BasicView {
    }

    public interface ExtensiveView extends TypicalView {
    }
}
