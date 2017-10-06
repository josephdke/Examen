package co.com.etn.examen.repository;

/**
 * Created by leidyzulu on 26/09/17.
 */

public class RepositoryError extends Exception {

    private int idError;

    public RepositoryError(String message) {
        super(message);
    }

    public int getIdError() {
        return idError;
    }

    public void setIdError(int idError) {
        this.idError = idError;
    }

}
