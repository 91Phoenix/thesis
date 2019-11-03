package common.recipes;

import java.util.List;

public class GetAccountsResponse {
  private List<GetRecipeResponse> accounts;

  public GetAccountsResponse() {
  }

  public GetAccountsResponse(List<GetRecipeResponse> accounts) {
    this.accounts = accounts;
  }

  public List<GetRecipeResponse> getAccounts() {
    return accounts;
  }

  public void setAccounts(List<GetRecipeResponse> accounts) {
    this.accounts = accounts;
  }
}
