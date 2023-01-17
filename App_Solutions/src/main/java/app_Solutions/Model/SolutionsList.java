package app_Solutions.Model;

import java.util.List;

public class SolutionsList {
    List<Solutions> solutionsList;

    public SolutionsList() {
    }

    public SolutionsList(List<Solutions> solutionsList) {
        this.solutionsList = solutionsList;
    }

    public List<Solutions> getSolutionsList() {
        return solutionsList;
    }

    public void setSolutionsList(List<Solutions> solutionsList) {
        this.solutionsList = solutionsList;
    }
}
