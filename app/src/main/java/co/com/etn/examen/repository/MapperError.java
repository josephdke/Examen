package co.com.etn.examen.repository;

import co.com.etn.examen.helper.Constants;

import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by leidyzulu on 26/09/17.
 */

public class MapperError {


    public static RepositoryError convertRetrofitErrorToRepositoryError(RetrofitError retrofitError) {
        RepositoryError repositoryError;

        repositoryError = valdiateTimeOutToGetRepositoryError(retrofitError);
        if (repositoryError != null) {
            return repositoryError;
        }

        repositoryError = validateTheBodyToGetRepositoryError(retrofitError);
        if (repositoryError != null) {
            return repositoryError;
        }

        return getDefaulError();
    }

    private static RepositoryError validateTheBodyToGetRepositoryError(RetrofitError retrofitError) {
        RepositoryError repositoryError = null;
        Response response = retrofitError.getResponse();
        if (response != null) {
            int errorId = response.getStatus();
            String mensaje = Constants.DEFAUL_ERROR;
            if (errorId == Constants.UNAUTHORIZED_ERROR_CODE || errorId == Constants.NOT_FOUND_ERROR_CODE) {
               /* try {
                    ErrorDTO errorDTO = (ErrorDTO) retrofitError.getBodyAs(ErrorDTO.class);
                    if (errorDTO != null) {
                        mensaje = errorDTO.getMessage();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }*/
            }
            repositoryError = new RepositoryError(mensaje);
            repositoryError.setIdError(errorId);
        }
        return repositoryError;
    }

    private static RepositoryError valdiateTimeOutToGetRepositoryError(RetrofitError retrofitError) {
        if (retrofitError.getCause() != null && retrofitError.getCause() instanceof SocketTimeoutException
                || retrofitError.getCause() instanceof InterruptedIOException) {
            RepositoryError repositoryError = new RepositoryError(Constants.REQUEST_TIMEOUT_ERROR_MESSAGE);
            repositoryError.setIdError(Constants.DEFAUL_ERROR_CODE);
            return repositoryError;
        }
        return null;
    }

    public static RepositoryError getDefaulError() {
        RepositoryError repositoryError = new RepositoryError(Constants.DEFAUL_ERROR);
        repositoryError.setIdError(Constants.DEFAUL_ERROR_CODE);
        return repositoryError;
    }
}
