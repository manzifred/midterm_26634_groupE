# 🖼️ How to Upload Your ERD Image

## ⚠️ IMPORTANT: Current Issue

The file `docs/erd-diagram.png` is currently just a placeholder (11 bytes). You need to replace it with your actual ERD diagram image!

## 📝 Steps to Fix:

### Option 1: Replace the File Locally (Recommended)

1. **Locate your ERD diagram image** on your computer
   - It should be a PNG, JPG, or other image file
   - Make sure it's the actual diagram, not a text file

2. **Delete the placeholder file**:
   ```bash
   rm docs/erd-diagram.png
   ```

3. **Copy your actual ERD image** to the docs folder:
   ```bash
   # Replace "path/to/your/erd.png" with your actual image path
   cp "path/to/your/erd.png" docs/erd-diagram.png
   ```

4. **Verify the file size** (should be more than 11 bytes!):
   ```bash
   ls -lh docs/erd-diagram.png
   ```

5. **Push to GitHub**:
   ```bash
   git add docs/erd-diagram.png
   git commit -m "Add actual ERD diagram image"
   git push origin main
   ```

### Option 2: Upload Directly on GitHub

1. Go to: https://github.com/manzifred/midterm_26634_groupE

2. Navigate to the `docs` folder

3. Click on `erd-diagram.png`

4. Click the trash icon to delete it

5. Go back to the `docs` folder

6. Click "Add file" → "Upload files"

7. Drag and drop your actual ERD image

8. Name it `erd-diagram.png`

9. Commit the changes

## 🎨 Creating an ERD Diagram

If you don't have an ERD diagram yet, you can create one using:

### Online Tools (Free):
- **Draw.io** (diagrams.net) - https://app.diagrams.net/
- **Lucidchart** - https://www.lucidchart.com/
- **dbdiagram.io** - https://dbdiagram.io/
- **ERDPlus** - https://erdplus.com/

### Desktop Tools:
- **MySQL Workbench** - Can generate ERD from database
- **pgAdmin** - PostgreSQL ERD tool
- **DBeaver** - Universal database tool with ERD

### What to Include in Your ERD:

Your diagram should show:

1. **All 9 Entities**:
   - Province
   - District
   - Sector
   - Cell
   - Village
   - User
   - Profile
   - Property
   - owner_property (join table)

2. **Relationships**:
   - Province → District (1:M)
   - District → Sector (1:M)
   - Sector → Cell (1:M)
   - Cell → Village (1:M)
   - Village → User (1:M)
   - User ↔ Profile (1:1)
   - User ↔ Property (M:M via owner_property)

3. **Key Fields**:
   - Primary keys (id)
   - Foreign keys (province_id, district_id, etc.)
   - Important attributes (name, code, email, etc.)

## ✅ After Uploading

Once you upload the actual image:

1. Wait 1-2 minutes for GitHub to process it

2. Visit your repository: https://github.com/manzifred/midterm_26634_groupE

3. The ERD diagram should now be visible in the README!

4. If still not showing, try:
   - Refresh the page (Ctrl+F5)
   - Open in incognito/private mode
   - Clear browser cache

## 🆘 Need Help?

If you're having trouble:

1. Make sure your image file is actually an image (PNG, JPG, etc.)
2. Check the file size - should be at least 50KB for a decent diagram
3. Try opening the image on your computer first to verify it's valid
4. Make sure the filename is exactly `erd-diagram.png`

---

**Current Status**: ❌ Placeholder file (needs replacement)  
**Target**: ✅ Actual ERD diagram image  
**Location**: `docs/erd-diagram.png`
