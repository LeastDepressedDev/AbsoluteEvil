package me.qigan.abse.mapping.auto;

public class QueuedSeq {
    public boolean finished = false;

    public void run() {finished = true;}
    public void finalise() {}
}
