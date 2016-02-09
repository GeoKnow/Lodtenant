package org.aksw.lodtenant.cli;

import org.apache.jena.graph.Node;

public class QuadCoordinate {
    protected Node g;
    protected Node s;
    protected Node p;
    protected Integer i;
    protected Integer c;

    public QuadCoordinate(Node g, Node s, Node p, Integer i, Integer c) {
        super();
        this.g = g;
        this.s = s;
        this.p = p;
        this.i = i;
        this.c = c;
    }



    public Node getG() {
        return g;
    }



    public Node getS() {
        return s;
    }

    public Node getP() {
        return p;
    }

    public Integer getI() {
        return i;
    }

    public Integer getC() {
        return c;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((c == null) ? 0 : c.hashCode());
        result = prime * result + ((i == null) ? 0 : i.hashCode());
        result = prime * result + ((p == null) ? 0 : p.hashCode());
        result = prime * result + ((s == null) ? 0 : s.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        QuadCoordinate other = (QuadCoordinate) obj;
        if (c == null) {
            if (other.c != null)
                return false;
        } else if (!c.equals(other.c))
            return false;
        if (i == null) {
            if (other.i != null)
                return false;
        } else if (!i.equals(other.i))
            return false;
        if (p == null) {
            if (other.p != null)
                return false;
        } else if (!p.equals(other.p))
            return false;
        if (s == null) {
            if (other.s != null)
                return false;
        } else if (!s.equals(other.s))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "RdfCoordinate [s=" + s + ", p=" + p + ", i=" + i + ", c=" + c
                + "]";
    }
}
