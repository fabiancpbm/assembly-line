package dev.assemblyline.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Linha de produção.
 */
public class Production {

    /** Lista de linhas de produção. */
    private List<AssemblyLine> assemblyLineList;

    /**
     * Construtor.
     */
    public Production() {
        this.assemblyLineList = new ArrayList<AssemblyLine>();
    }

    /**
     * @return {@link #assemblyLineList}
     */
    public List<AssemblyLine> getAssemblyLineList() {
        return this.assemblyLineList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Production that = (Production) o;
        return Objects.equals(this.assemblyLineList, that.assemblyLineList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.assemblyLineList);
    }

    @Override
    public String toString() {
        String result = "";
        int assemblyLineNumber = 1;
        for (AssemblyLine assemblyLine : this.assemblyLineList) {
            result += "Linha de montagem " + assemblyLineNumber + ":\n";
            result += assemblyLine.toString() + "\n\n";
            assemblyLineNumber++;
        }
        return result.substring(0, result.length() - 2);
    }
}
