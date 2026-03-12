# How to Add the ERD Image to Your README

## Step 1: Create the docs folder

If it doesn't exist already, create a folder called `docs` in your project root:

```bash
mkdir docs
```

## Step 2: Save your ERD image

Save the ERD diagram image you showed me as `erd-diagram.png` in the `docs` folder.

Your file structure should look like:
```
midterm_26634_groupE/
├── docs/
│   └── erd-diagram.png    <-- Your ERD image here
├── src/
├── pom.xml
└── README.md
```

## Step 3: Commit and push

```bash
git add docs/erd-diagram.png
git add README.md
git commit -m "Add professional README with ERD diagram"
git push origin main
```

## Alternative: If you want to use a different image name or location

If you want to use a different name or location for your image, update this line in README.md:

```markdown
![ERD Diagram](docs/erd-diagram.png)
```

Change it to match your image location, for example:
```markdown
![ERD Diagram](images/my-erd.png)
```

## Verification

After pushing to GitHub, visit your repository at:
https://github.com/manzifred/midterm_26634_groupE

The README should display beautifully with your ERD diagram visible!
