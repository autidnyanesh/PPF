# Apply the matching GBM changes

This agent can push to `autidnyanesh/PPF` but not to `autidnyanesh/GBM`.
The PPF module expects the JWT login app in the **GBM** repo.

On your PC, in the GBM folder (the one you already pushed):

```powershell
cd "D:\GBM Backend Application\GBM"
# copy gbm-jwt-flow.patch from this PPF repo into that folder, then:
git apply gbm-jwt-flow.patch
git add .
git commit -m "Issue JWT from GBM login and expose GBM Home"
git push
```

If `git apply` fails, copy these files from this PPF PR’s `gbm-jwt-flow.patch`, or re-clone and ask a **local** Cursor Agent (This Computer) to apply the patch.

Both apps must use the same secret:

```powershell
$env:GBM_TOKEN_SECRET="gbm-local-dev-secret-please-change-32bytes!"
```
