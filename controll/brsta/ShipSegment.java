package brsta;

public class ShipSegment {
    private int xKoord=-1;
    private int yKoord=-1;
    private boolean hit;


    public ShipSegment(int x ,int y){
        this.xKoord=x;
        this.yKoord=y;
        this.hit=false;
    }

    public int getxKoord() {

        return this.xKoord;
    }

    public int getyKoord() {

        return this.yKoord;
    }

    public boolean isequalSegment( ShipSegment Segment){
        return (this.xKoord== Segment.xKoord && Segment.yKoord==this.yKoord);
    }

    public void HitSegment() {
        this.hit=true;

    }

    public boolean isHit() {
        return hit;
    }
}
