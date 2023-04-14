package testUtils;

import lombok.SneakyThrows;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {

    private int retryCount = 0;

    @SneakyThrows
    @Override
    public boolean retry(ITestResult iTestResult) {
        int maxRetryCount = new AppPropertiesLoader().getAppProperty("retry.count");

        if (retryCount < maxRetryCount) {
            System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>> Retrying test with status "
                    + getResultsStatusName(iTestResult.getStatus())
                    + " for the " + (retryCount + 1)
                    + " time(s). <<<<<<<<<<<<<<<<<<<<<<<<<<");
            retryCount++;
            return true;
        }
        return false;
    }

    public String getResultsStatusName(int status) {
        String resultName = null;
        switch (status){
            case 1:
                resultName = "SUCCESS";
                break;
            case 2:
                resultName = "FAILURE";
                break;
            case 3:
                resultName = "SKIP";
                break;
        }
        return resultName;
    }
}
