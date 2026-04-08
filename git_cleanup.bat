git add .
git commit -m "last updates"
git checkout --orphan latest_branch
git add -A
git commit -am "Initial commit"
git branch -D main
git branch -m main
git push -f origin main
git checkout main
REM git branch -D latest_branch
git reflog expire --expire=now --all
git gc --prune=now --aggressive
pause