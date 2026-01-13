# SMART STOCK MANAGEMENT SYSTEM

# BACKEND API DOCUMENTATION

# (for Front-End Developers)

# 

# ====================================

# BASE URL

# ====================================

# http://localhost:8080

# 

# ====================================

# AUTHENTICATION (JWT)

# ====================================

# 

# LOGIN

# POST /api/auth/login

# 

# Request Body:

# {

# &nbsp; "username": "admin",

# &nbsp; "password": "admin123"

# }

# 

# Response:

# {

# &nbsp; "token": "JWT\_TOKEN",

# &nbsp; "username": "admin",

# &nbsp; "roles": \["ROLE\_ADMIN"]

# }

# 

# AUTH HEADER (for all secured endpoints):

# Authorization: Bearer JWT\_TOKEN

# 

# ====================================

# ROLES

# ====================================

# 

# ADMIN:

# \- Full access to all APIs

# 

# COMPTABLE:

# \- Read stock

# \- Stock movements

# \- Dashboard stats

# \- NO access to achats

# 

# ====================================

# PRODUITS

# ====================================

# 

# GET ALL PRODUITS

# GET /api/produits

# 

# Response:

# \[

# &nbsp; {

# &nbsp;   "id": 1,

# &nbsp;   "designation": "Ciment CPJ",

# &nbsp;   "unite": "KG",

# &nbsp;   "stockMin": 100,

# &nbsp;   "stockActuel": 80,

# &nbsp;   "stockAlert": true,

# &nbsp;   "active": true

# &nbsp; }

# ]

# 

# CREATE PRODUIT (ADMIN)

# POST /api/produits

# {

# &nbsp; "designation": "Sable",

# &nbsp; "unite": "KG",

# &nbsp; "stockMin": 500

# }

# 

# UPDATE PRODUIT (ADMIN)

# PUT /api/produits/{id}

# 

# DELETE PRODUIT (ADMIN)

# DELETE /api/produits/{id}

# 

# STOCK ALERTS (minimum stock)

# GET /api/produits/alerts

# (Returns only products with stockAlert = true)

# 

# ====================================

# STOCK MOVEMENTS

# ====================================

# 

# ADD ENTREE

# POST /api/movements/entree

# {

# &nbsp; "produitId": 1,

# &nbsp; "quantite": 100,

# &nbsp; "commentaire": "Achat fournisseur"

# }

# 

# ADD SORTIE

# POST /api/movements/sortie

# {

# &nbsp; "produitId": 1,

# &nbsp; "quantite": 20,

# &nbsp; "commentaire": "Vente client"

# }

# 

# SEARCH + PAGINATION

# GET /api/movements/search/paged

# 

# Query Params:

# produitId=

# type=ENTREE|SORTIE

# from=YYYY-MM-DD

# to=YYYY-MM-DD

# page=0

# size=20

# sort=date,desc

# 

# ====================================

# ACHATS (ADMIN ONLY)

# ====================================

# 

# GET ALL ACHATS

# GET /api/achats

# 

# CREATE ACHAT (DRAFT)

# POST /api/achats

# {

# &nbsp; "referenceFacture": "FAC-001",

# &nbsp; "date": "2026-01-10",

# &nbsp; "tvaRate": 20,

# &nbsp; "fournisseurId": 1

# }

# 

# ADD LINE TO ACHAT

# POST /api/achats/{id}/lines

# {

# &nbsp; "produitId": 1,

# &nbsp; "quantite": 10,

# &nbsp; "prixUnitaireHT": 50

# }

# 

# VALIDATE ACHAT

# POST /api/achats/{id}/validate

# 

# ====================================

# DASHBOARD / STATS

# ====================================

# 

# GLOBAL EXPENSES

# GET /api/stats/depenses

# Optional:

# ?from=YYYY-MM-DD\&to=YYYY-MM-DD

# 

# Response:

# {

# &nbsp; "totalHT": 500,

# &nbsp; "totalTVA": 100,

# &nbsp; "totalTTC": 600

# }

# 

# EXPENSES BY FOURNISSEUR

# GET /api/stats/depenses/fournisseurs

# 

# STOCK CONSUMPTION

# GET /api/stats/stock/consommation

# 

# Response:

# \[

# &nbsp; {

# &nbsp;   "produitId": 1,

# &nbsp;   "designation": "Ciment",

# &nbsp;   "totalEntree": 1000,

# &nbsp;   "totalSortie": 300,

# &nbsp;   "stockActuel": 700

# &nbsp; }

# ]

# 

# ====================================

# EXPORT

# ====================================

# 

# EXPORT STOCK (EXCEL)

# GET /api/export/stock/excel

# → Download stock.xlsx

# 

# EXPORT ACHATS (EXCEL)

# GET /api/export/achats/excel

# Optional:

# ?from=YYYY-MM-DD\&to=YYYY-MM-DD

# → Download achats.xlsx

# 

# ====================================

# ERROR FORMAT

# ====================================

# 

# {

# &nbsp; "status": 403,

# &nbsp; "error": "Forbidden",

# &nbsp; "message": "Access denied"

# }

# 

# ====================================

# IMPORTANT FRONTEND RULES

# ====================================

# 

# \- Frontend MUST NOT calculate stock

# \- Frontend MUST NOT decide alerts

# \- Backend is the single source of truth

# \- Frontend only displays data

# 

# ====================================

# BACKEND STATUS

# ====================================

# 

# ✔ Complete

# ✔ Secure (JWT)

# ✔ Role-based access

# ✔ Ready for Angular / React / Vue

# ✔ Scalable

# 

# END OF DOCUMENT



