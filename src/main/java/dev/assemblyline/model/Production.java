package dev.assemblyline.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Linha de produção.
 */
public class Production {

    /** Lista de linhas de produção. */
    private List<AssemblyLine> assemblyLines;

    /**
     * Construtor.
     */
    public Production() {
        this.assemblyLines = new ArrayList<AssemblyLine>();
    }

    /**
     * @return {@link #assemblyLines}
     */
    public List<AssemblyLine> getAssemblyLines() {
        return this.assemblyLines;
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
        return Objects.equals(this.assemblyLines, that.assemblyLines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.assemblyLines);
    }

    @Override
    public String toString() {
        String result = "";
        int assemblyLineNumber = 1;
        for (AssemblyLine assemblyLine : this.assemblyLines) {
            result += "Linha de montagem " + assemblyLineNumber + ":\n";
            result += assemblyLine.toString() + "\n\n";
            assemblyLineNumber++;
        }
        return result.substring(0, result.length() - 2);
    }
}