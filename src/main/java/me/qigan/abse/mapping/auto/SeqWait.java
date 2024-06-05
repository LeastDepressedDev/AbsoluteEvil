package me.qigan.abse.mapping.auto;

public class SeqWait extends QueuedSeq{
    public int ticks;

    public SeqWait(int tick) {
        this.ticks = tick;
    }

    @Override
    public void run() {
        if (ticks > 0) ticks--;
        else finished = true;
    }
}
