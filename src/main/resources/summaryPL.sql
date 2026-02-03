select row_number() Over() sno,i.heading,i.maingroup,sum(i.bud)bud,sum(i.act)act,sum(i.py)py,sum(i.ybud)ybud,sum(i.yact)yact,sum(i.ypy)ypy,sum(i.fbud)fbud,((sum(i.yact)/( select monthsequence from previousyearactual where year=?2 and month=?4 and clientcode=?1 group by monthsequence))*12)estimate,sum(i.fpy)fpy from(
select a.heading,a.maingroup,a.bud,a.act,a.py,0 ybud,0 yact,0 ypy,0 fbud,0 estimate,0 fpy from(
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(
select 'Profit And Loss Accounts'heading, maingroup,sum(amount)bud,0 act, 0 py from budget where clientcode=?1 and year=?2 and month=?4 and maingroup='Sales'union
select 'Profit And Loss Accounts'heading,maingroup,0 bud,sum(amount)act, 0 py from previousyearactual where clientcode=?1 and year=?2 and month=?4 and maingroup='Sales' union
select 'Profit And Loss Accounts'heading,maingroup,0 bud, 0 act,sum(amount) py from previousyearactual where clientcode=?1 and year=?3 and month=?4 and maingroup='Sales') t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(SELECT
    'Profit And Loss Accounts' AS heading,
    'Gross Profit' AS maingroup,
    SUM(
        CASE 
            WHEN maingroup = 'Sales' THEN amount
            when maingroup='Other Income' and subgroup='Other Operating Income' then amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            ELSE 0
        END
    ) AS bud,
    0 AS act,
    0 AS py
FROM budget
WHERE clientcode = ?1
  AND year = ?2
  AND month = ?4
  union
  SELECT
    'Profit And Loss Accounts' AS heading,
    'Gross Profit' AS maingroup,
    0 bud,
    SUM(
        CASE 
            WHEN maingroup = 'Sales' THEN amount
             when maingroup='Other Income' and subgroup='Other Operating Income' then amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            ELSE 0
        END
    ) AS act,
    0 AS py
FROM previousyearactual
WHERE clientcode = ?1
  AND year = ?2
  AND month = ?4
  union
  SELECT
    'Profit And Loss Accounts' AS heading,
    'Gross Profit' AS maingroup,
    0 bud,
    0 act,
    SUM(
        CASE 
            WHEN maingroup = 'Sales' THEN amount
            when maingroup='Other Income' and subgroup='Other Operating Income' then amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            ELSE 0
        END
    ) AS py
FROM previousyearactual
WHERE clientcode = ?1
  AND year = ?3
  AND month = ?4
)t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(SELECT
    'Profit And Loss Accounts' AS heading,
    'Contribution' AS maingroup,
    SUM(
        CASE 
            WHEN maingroup = 'Sales' THEN  amount
             when maingroup='Other Income' and subgroup='Other Operating Income' then amount
            WHEN maingroup = 'Purchase' THEN - amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN + amount
             when maingroup='Conversion Expenses' then - amount
            ELSE 0
        END
    ) AS bud,
    0 AS act,
    0 AS py
FROM budget
WHERE clientcode = ?1
  AND year = ?2
  AND month = ?4
  union
  SELECT
    'Profit And Loss Accounts' AS heading,
   'Contribution' AS maingroup,
    0 bud,
    SUM(
        CASE 
            WHEN maingroup = 'Sales' THEN  amount
             when maingroup='Other Income' and subgroup='Other Operating Income' then amount
            WHEN maingroup = 'Purchase' THEN - amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN + amount
             when maingroup='Conversion Expenses' then - amount
            ELSE 0
        END
    ) AS act,
    0 AS py
FROM previousyearactual
WHERE clientcode = ?1
  AND year = ?2
  AND month = ?4
  union
  SELECT
    'Profit And Loss Accounts' AS heading,
    'Contribution' AS maingroup,
    0 bud,
    0 act,
    SUM(
        CASE 
            WHEN maingroup = 'Sales' THEN  amount
             when maingroup='Other Income' and subgroup='Other Operating Income' then amount
            WHEN maingroup = 'Purchase' THEN - amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN + amount
             when maingroup='Conversion Expenses' then - amount
            ELSE 0
        END
    ) AS py
FROM previousyearactual
WHERE clientcode = ?1
  AND year = ?3
  AND month = ?4
)t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(
select 'Profit And Loss Accounts'heading, maingroup,sum(amount)bud,0 act, 0 py from budget where clientcode=?1 and year=?2 and month=?4 and maingroup='Over Head' union
select 'Profit And Loss Accounts'heading,maingroup,0 bud,sum(amount)act, 0 py from previousyearactual where clientcode=?1 and year=?2 and month=?4 and maingroup='Over Head'  union
select 'Profit And Loss Accounts'heading,maingroup,0 bud, 0 act,sum(amount) py from previousyearactual where clientcode=?1 and year=?3 and month=?4 and maingroup='Over Head') t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(SELECT 'Profit And Loss Accounts'heading,
    'Profit Befor Dep & Tax' AS maingroup,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            
            ELSE 0
        END
    ) AS bud,0 act, 0 py
FROM budget
WHERE 
    clientcode = ?1
    AND year = ?2
    AND month = ?4union
    SELECT 'Profit And Loss Accounts'heading,
    'Profit Befor Dep & Tax' AS maingroup,0 bud,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            
            ELSE 0
        END
    )  act, 0 py
FROM previousyearactual
WHERE 
    clientcode = ?1
    AND year = ?2
    AND month = ?4
    union
    SELECT 'Profit And Loss Accounts'heading,
    'Profit Befor Dep & Tax' AS maingroup,0 bud,0 act,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            
            ELSE 0
        END
    )  py
FROM previousyearactual
WHERE 
    clientcode = ?1
    AND year = ?3
    AND month = ?4)t group by t.heading,t.maingroup
    union
    select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(SELECT 'Profit And Loss Accounts'heading,
    'Profit Befor Tax' AS maingroup,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Depreciation' then -amount
            ELSE 0
        END
    ) AS bud,0 act, 0 py
FROM budget
WHERE 
    clientcode = ?1
    AND year = ?2
    AND month = ?4union
    SELECT 'Profit And Loss Accounts'heading,
    'Profit Befor Tax' AS maingroup,0 bud,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Depreciation' then -amount
            ELSE 0
        END
    )  act, 0 py
FROM previousyearactual
WHERE 
    clientcode = ?1
    AND year = ?2
    AND month = ?4
    union
    SELECT 'Profit And Loss Accounts'heading,
    'Profit Befor Tax' AS maingroup,0 bud,0 act,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Depreciation' then - amount
            
            ELSE 0
        END
    )  py
FROM previousyearactual
WHERE 
    clientcode = ?1
    AND year = ?3
    AND month = ?4)t group by t.heading,t.maingroup
    union
    select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(SELECT 'Profit And Loss Accounts'heading,
    'Profit After Tax' AS maingroup,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Depreciation' then -amount
            when maingroup='Over Head' and subgroup='Income Tax' then -amount
            ELSE 0
        END
    ) AS bud,0 act, 0 py
FROM budget
WHERE 
    clientcode = ?1
    AND year = ?2
    AND month = ?4union
    SELECT 'Profit And Loss Accounts'heading,
    'Profit After Tax' AS maingroup,0 bud,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Depreciation' then -amount
            when maingroup='Over Head' and subgroup='Income Tax' then -amount
            ELSE 0
        END
    )  act, 0 py
FROM previousyearactual
WHERE 
    clientcode = ?1
    AND year = ?2
    AND month = ?4
    union
    SELECT 'Profit And Loss Accounts'heading,
    'Profit After Tax' AS maingroup,0 bud,0 act,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Depreciation' then - amount
            when maingroup='Over Head' and subgroup='Income Tax' then -amount
            
            ELSE 0
        END
    )  py
FROM previousyearactual
WHERE 
    clientcode = ?1
    AND year = ?3
    AND month = ?4)t group by t.heading,t.maingroup
    union
    select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(SELECT 'Profit And Loss Accounts'heading,
    'Cash Profit' AS maingroup,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Over Head' and subgroup='Income Tax' then -amount
            when maingroup='Over Head' and subgroup ='CSR Activity Exp' then -amount
            ELSE 0
        END
    ) AS bud,0 act, 0 py
FROM budget
WHERE 
    clientcode = ?1
    AND year = ?2
    AND month = ?4union
    SELECT 'Profit And Loss Accounts'heading,
    'Cash Profit' AS maingroup,0 bud,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Over Head' and subgroup='Income Tax' then -amount
            when maingroup='Over Head' and subgroup ='CSR Activity Exp' then -amount
            ELSE 0
        END
    )  act, 0 py
FROM previousyearactual
WHERE 
    clientcode = ?1
    AND year = ?2
    AND month = ?4
    union
    SELECT 'Profit And Loss Accounts'heading,
    'Cash Profit' AS maingroup,0 bud,0 act,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Over Head' and subgroup='Income Tax' then -amount
            when maingroup='Over Head' and subgroup ='CSR Activity Exp' then -amount
            
            ELSE 0
        END
    )  py
FROM previousyearactual
WHERE 
    clientcode = ?1
    AND year = ?3
    AND month = ?4)t group by t.heading,t.maingroup)a
    union
    select u.heading,u.maingroup,0 bud, 0 act,0 py,u.ybud,u.yact,u.ypy,0 fbud,0 estimate,0 fpy from(
select t.heading,t.maingroup,sum(t.bud)ybud,sum(t.act)yact,sum(t.py)ypy from(
select 'Profit And Loss Accounts'heading, maingroup,sum(amount)bud,0 act, 0 py from budget where clientcode=?1 and year = ?2
  AND  monthsequence <=( select monthsequence from budget  where year=?2 and month=?4 and clientcode=?1 group by monthsequence) and maingroup='Sales'union
select 'Profit And Loss Accounts'heading,maingroup,0 bud,sum(amount)act, 0 py from previousyearactual where clientcode=?1 and year = ?2
  AND  monthsequence <=( select monthsequence from previousyearactual  where year=?2 and month=?4 and clientcode=?1 group by monthsequence) and maingroup='Sales' union
select 'Profit And Loss Accounts'heading,maingroup,0 bud, 0 act,sum(amount) py from previousyearactual where clientcode=?1 and year = ?3
  AND  monthsequence <=( select monthsequence from previousyearactual  where year=?3 and month=?4 and clientcode=?1 group by monthsequence) and maingroup='Sales') t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(SELECT
    'Profit And Loss Accounts' AS heading,
    'Gross Profit' AS maingroup,
    SUM(
        CASE 
            WHEN maingroup = 'Sales' THEN amount
            when maingroup='Other Income' and subgroup='Other Operating Income' then amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            ELSE 0
        END
    ) AS bud,
    0 AS act,
    0 AS py
FROM budget
WHERE clientcode = ?1
  AND year = ?2
  AND  monthsequence <=( select monthsequence from budget  where year=?2 and month=?4 and clientcode=?1 group by monthsequence)
  union
  SELECT
    'Profit And Loss Accounts' AS heading,
    'Gross Profit' AS maingroup,
    0 bud,
    SUM(
        CASE 
            WHEN maingroup = 'Sales' THEN amount
             when maingroup='Other Income' and subgroup='Other Operating Income' then amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            ELSE 0
        END
    ) AS act,
    0 AS py
FROM previousyearactual
WHERE clientcode = ?1
  AND year = ?2
  AND  monthsequence <=( select monthsequence from previousyearactual  where year=?2 and month=?4 and clientcode=?1 group by monthsequence)
  union
  SELECT
    'Profit And Loss Accounts' AS heading,
    'Gross Profit' AS maingroup,
    0 bud,
    0 act,
    SUM(
        CASE 
            WHEN maingroup = 'Sales' THEN amount
            when maingroup='Other Income' and subgroup='Other Operating Income' then amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            ELSE 0
        END
    ) AS py
FROM previousyearactual
WHERE clientcode = ?1
  AND year = ?3
  AND  monthsequence <=( select monthsequence from previousyearactual  where year=?3 and month=?4 and clientcode=?1 group by monthsequence)
)t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(SELECT
    'Profit And Loss Accounts' AS heading,
    'Contribution' AS maingroup,
    SUM(
        CASE 
            WHEN maingroup = 'Sales' THEN  amount
             when maingroup='Other Income' and subgroup='Other Operating Income' then amount
            WHEN maingroup = 'Purchase' THEN - amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN + amount
             when maingroup='Conversion Expenses' then - amount
            ELSE 0
        END
    ) AS bud,
    0 AS act,
    0 AS py
FROM budget
WHERE clientcode = ?1
  AND year = ?2
  AND  monthsequence <=( select monthsequence from budget  where year=?2 and month=?4 and clientcode=?1 group by monthsequence)
  union
  SELECT
    'Profit And Loss Accounts' AS heading,
   'Contribution' AS maingroup,
    0 bud,
    SUM(
        CASE 
            WHEN maingroup = 'Sales' THEN  amount
             when maingroup='Other Income' and subgroup='Other Operating Income' then amount
            WHEN maingroup = 'Purchase' THEN - amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN + amount
             when maingroup='Conversion Expenses' then - amount
            ELSE 0
        END
    ) AS act,
    0 AS py
FROM previousyearactual
WHERE clientcode = ?1
  AND year = ?2
  AND  monthsequence <=( select monthsequence from previousyearactual  where year=?2 and month=?4 and clientcode=?1 group by monthsequence)
  union
  SELECT
    'Profit And Loss Accounts' AS heading,
    'Contribution' AS maingroup,
    0 bud,
    0 act,
    SUM(
        CASE 
            WHEN maingroup = 'Sales' THEN  amount
             when maingroup='Other Income' and subgroup='Other Operating Income' then amount
            WHEN maingroup = 'Purchase' THEN - amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN + amount
             when maingroup='Conversion Expenses' then - amount
            ELSE 0
        END
    ) AS py
FROM previousyearactual
WHERE clientcode = ?1
  AND year = ?3
  AND  monthsequence <=( select monthsequence from previousyearactual  where year=?3 and month=?4 and clientcode=?1 group by monthsequence)
)t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(
select 'Profit And Loss Accounts'heading, maingroup,sum(amount)bud,0 act, 0 py from budget where clientcode=?1 and year=?2 AND  monthsequence <=( select monthsequence from budget  where year=?2 and month=?4 and clientcode=?1 group by monthsequence) and maingroup='Over Head' union
select 'Profit And Loss Accounts'heading,maingroup,0 bud,sum(amount)act, 0 py from previousyearactual where clientcode=?1 and year=?2 AND  monthsequence <=( select monthsequence from previousyearactual  where year=?2 and month=?4 and clientcode=?1 group by monthsequence) and maingroup='Over Head'  union
select 'Profit And Loss Accounts'heading,maingroup,0 bud, 0 act,sum(amount) py from previousyearactual where clientcode=?1 and year=?3 AND  monthsequence <=( select monthsequence from previousyearactual  where year=?3 and month=?4 and clientcode=?1 group by monthsequence) and maingroup='Over Head' ) t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(SELECT 'Profit And Loss Accounts'heading,
    'Profit Befor Dep & Tax' AS maingroup,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            
            ELSE 0
        END
    ) AS bud,0 act, 0 py
FROM budget
WHERE 
    clientcode = ?1
    AND year = ?2
    AND   monthsequence <=( select monthsequence from budget  where year=?2 and month=?4 and clientcode=?1 group by monthsequence) union
    SELECT 'Profit And Loss Accounts'heading,
    'Profit Befor Dep & Tax' AS maingroup,0 bud,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            
            ELSE 0
        END
    )  act, 0 py
FROM previousyearactual
WHERE 
    clientcode = ?1
    AND year = ?2
    AND  monthsequence <=( select monthsequence from previousyearactual  where year=?2 and month=?4 and clientcode=?1 group by monthsequence)
    union
    SELECT 'Profit And Loss Accounts'heading,
    'Profit Befor Dep & Tax' AS maingroup,0 bud,0 act,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            
            ELSE 0
        END
    )  py
FROM previousyearactual
WHERE 
    clientcode = ?1
    AND year = ?3
   AND  monthsequence <=( select monthsequence from previousyearactual  where year=?3 and month=?4 and clientcode=?1 group by monthsequence))t group by t.heading,t.maingroup
    union
    select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(SELECT 'Profit And Loss Accounts'heading,
    'Profit Befor Tax' AS maingroup,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Depreciation' then -amount
            ELSE 0
        END
    ) AS bud,0 act, 0 py
FROM budget
WHERE 
    clientcode = ?1
    AND year = ?2
    AND   monthsequence <=( select monthsequence from budget  where year=?2 and month=?4 and clientcode=?1 group by monthsequence)union
    SELECT 'Profit And Loss Accounts'heading,
    'Profit Befor Tax' AS maingroup,0 bud,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Depreciation' then -amount
            ELSE 0
        END
    )  act, 0 py
FROM previousyearactual
WHERE 
    clientcode = ?1
    AND year = ?2
   AND  monthsequence <=( select monthsequence from previousyearactual  where year=?2 and month=?4 and clientcode=?1 group by monthsequence)
    union
    SELECT 'Profit And Loss Accounts'heading,
    'Profit Befor Tax' AS maingroup,0 bud,0 act,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Depreciation' then - amount
            
            ELSE 0
        END
    )  py
FROM previousyearactual
WHERE 
    clientcode = ?1
    AND year = ?3
    AND  monthsequence <=( select monthsequence from previousyearactual  where year=?3 and month=?4 and clientcode=?1 group by monthsequence))t group by t.heading,t.maingroup
    union
    select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(SELECT 'Profit And Loss Accounts'heading,
    'Profit After Tax' AS maingroup,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Depreciation' then -amount
            when maingroup='Over Head' and subgroup='Income Tax' then -amount
            ELSE 0
        END
    ) AS bud,0 act, 0 py
FROM budget
WHERE 
    clientcode = ?1
    AND year = ?2
   AND  monthsequence <=( select monthsequence from budget  where year=?2 and month=?4 and clientcode=?1 group by monthsequence)union
    SELECT 'Profit And Loss Accounts'heading,
    'Profit After Tax' AS maingroup,0 bud,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Depreciation' then -amount
            when maingroup='Over Head' and subgroup='Income Tax' then -amount
            ELSE 0
        END
    )  act, 0 py
FROM previousyearactual
WHERE 
    clientcode = ?1
    AND year = ?2
    AND  monthsequence <=( select monthsequence from previousyearactual  where year=?2 and month=?4 and clientcode=?1 group by monthsequence)
    union
    SELECT 'Profit And Loss Accounts'heading,
    'Profit After Tax' AS maingroup,0 bud,0 act,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Depreciation' then - amount
            when maingroup='Over Head' and subgroup='Income Tax' then -amount
            
            ELSE 0
        END
    )  py
FROM previousyearactual
WHERE 
    clientcode = ?1
    AND year = ?3
    AND  monthsequence <=( select monthsequence from previousyearactual  where year=?3 and month=?4 and clientcode=?1 group by monthsequence))t group by t.heading,t.maingroup
    union
    select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(SELECT 'Profit And Loss Accounts'heading,
    'Cash Profit' AS maingroup,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Over Head' and subgroup='Income Tax' then -amount
            when maingroup='Over Head' and subgroup ='CSR Activity Exp' then -amount
            ELSE 0
        END
    ) AS bud,0 act, 0 py
FROM budget
WHERE 
    clientcode = ?1
    AND year = ?2
   AND  monthsequence <=( select monthsequence from budget  where year=?2 and month=?4 and clientcode=?1 group by monthsequence)union
    SELECT 'Profit And Loss Accounts'heading,
    'Cash Profit' AS maingroup,0 bud,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Over Head' and subgroup='Income Tax' then -amount
            when maingroup='Over Head' and subgroup ='CSR Activity Exp' then -amount
            ELSE 0
        END
    )  act, 0 py
FROM previousyearactual
WHERE 
    clientcode = ?1
    AND year = ?2
   AND  monthsequence <=( select monthsequence from previousyearactual  where year=?2 and month=?4 and clientcode=?1 group by monthsequence)
    union
    SELECT 'Profit And Loss Accounts'heading,
    'Cash Profit' AS maingroup,0 bud,0 act,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Over Head' and subgroup='Income Tax' then -amount
            when maingroup='Over Head' and subgroup ='CSR Activity Exp' then -amount
            
            ELSE 0
        END
    )  py
FROM previousyearactual
WHERE 
    clientcode = ?1
    AND year = ?3
    AND  monthsequence <=( select monthsequence from previousyearactual  where year=?3 and month=?4 and clientcode=?1 group by monthsequence))t group by t.heading,t.maingroup)u
    union
    select o.heading,o.maingroup,0 bud, 0 act,0 py,0 ybud,0 yact,0 ypy,o.fbud,0 estimate,o.fpy from(
select t.heading,t.maingroup,sum(t.bud)fbud,sum(t.act)estimate,sum(t.py)fpy from(
select 'Profit And Loss Accounts'heading, maingroup,sum(amount)bud,0 act, 0 py from budget where clientcode=?1 and year = ?2 and maingroup='Sales'union
select 'Profit And Loss Accounts'heading,maingroup,0 bud,sum(amount)act, 0 py from previousyearactual where clientcode=?1 and year = ?2
   and maingroup='Sales' union
select 'Profit And Loss Accounts'heading,maingroup,0 bud, 0 act,sum(amount) py from previousyearactual where clientcode=?1 and year = ?3
   and maingroup='Sales') t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(SELECT
    'Profit And Loss Accounts' AS heading,
    'Gross Profit' AS maingroup,
    SUM(
        CASE 
            WHEN maingroup = 'Sales' THEN amount
            when maingroup='Other Income' and subgroup='Other Operating Income' then amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            ELSE 0
        END
    ) AS bud,
    0 AS act,
    0 AS py
FROM budget
WHERE clientcode = ?1
  AND year = ?2
  
  union
  SELECT
    'Profit And Loss Accounts' AS heading,
    'Gross Profit' AS maingroup,
    0 bud,
    SUM(
        CASE 
            WHEN maingroup = 'Sales' THEN amount
             when maingroup='Other Income' and subgroup='Other Operating Income' then amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            ELSE 0
        END
    ) AS act,
    0 AS py
FROM previousyearactual
WHERE clientcode = ?1
  AND year = ?2
  
  union
  SELECT
    'Profit And Loss Accounts' AS heading,
    'Gross Profit' AS maingroup,
    0 bud,
    0 act,
    SUM(
        CASE 
            WHEN maingroup = 'Sales' THEN amount
            when maingroup='Other Income' and subgroup='Other Operating Income' then amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            ELSE 0
        END
    ) AS py
FROM previousyearactual
WHERE clientcode = ?1
  AND year = ?3
  
)t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(SELECT
    'Profit And Loss Accounts' AS heading,
    'Contribution' AS maingroup,
    SUM(
        CASE 
            WHEN maingroup = 'Sales' THEN  amount
             when maingroup='Other Income' and subgroup='Other Operating Income' then amount
            WHEN maingroup = 'Purchase' THEN - amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN + amount
             when maingroup='Conversion Expenses' then - amount
            ELSE 0
        END
    ) AS bud,
    0 AS act,
    0 AS py
FROM budget
WHERE clientcode = ?1
  AND year = ?2
  
  union
  SELECT
    'Profit And Loss Accounts' AS heading,
   'Contribution' AS maingroup,
    0 bud,
    SUM(
        CASE 
            WHEN maingroup = 'Sales' THEN  amount
             when maingroup='Other Income' and subgroup='Other Operating Income' then amount
            WHEN maingroup = 'Purchase' THEN - amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN + amount
             when maingroup='Conversion Expenses' then - amount
            ELSE 0
        END
    ) AS act,
    0 AS py
FROM previousyearactual
WHERE clientcode = ?1
  AND year = ?2
  
  union
  SELECT
    'Profit And Loss Accounts' AS heading,
    'Contribution' AS maingroup,
    0 bud,
    0 act,
    SUM(
        CASE 
            WHEN maingroup = 'Sales' THEN  amount
             when maingroup='Other Income' and subgroup='Other Operating Income' then amount
            WHEN maingroup = 'Purchase' THEN - amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN + amount
             when maingroup='Conversion Expenses' then - amount
            ELSE 0
        END
    ) AS py
FROM previousyearactual
WHERE clientcode = ?1
  AND year = ?3
  
)t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(
select 'Profit And Loss Accounts'heading, maingroup,sum(amount)bud,0 act, 0 py from budget where clientcode=?1 and year=?2  and maingroup='Over Head' union
select 'Profit And Loss Accounts'heading,maingroup,0 bud,sum(amount)act, 0 py from previousyearactual where clientcode=?1 and year=?2  and maingroup='Over Head'  union
select 'Profit And Loss Accounts'heading,maingroup,0 bud, 0 act,sum(amount) py from previousyearactual where clientcode=?1 and year=?3  and maingroup='Over Head' ) t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(SELECT 'Profit And Loss Accounts'heading,
    'Profit Befor Dep & Tax' AS maingroup,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            
            ELSE 0
        END
    ) AS bud,0 act, 0 py
FROM budget
WHERE 
    clientcode = ?1
    AND year = ?2
	union
    SELECT 'Profit And Loss Accounts'heading,
    'Profit Befor Dep & Tax' AS maingroup,0 bud,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            
            ELSE 0
        END
    )  act, 0 py
FROM previousyearactual
WHERE 
    clientcode = ?1
    AND year = ?2
    
    union
    SELECT 'Profit And Loss Accounts'heading,
    'Profit Befor Dep & Tax' AS maingroup,0 bud,0 act,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            
            ELSE 0
        END
    )  py
FROM previousyearactual
WHERE 
    clientcode = ?1
    AND year = ?3
   )t group by t.heading,t.maingroup
    union
    select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(SELECT 'Profit And Loss Accounts'heading,
    'Profit Befor Tax' AS maingroup,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Depreciation' then -amount
            ELSE 0
        END
    ) AS bud,0 act, 0 py
FROM budget
WHERE 
    clientcode = ?1
    AND year = ?2
   union
    SELECT 'Profit And Loss Accounts'heading,
    'Profit Befor Tax' AS maingroup,0 bud,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Depreciation' then -amount
            ELSE 0
        END
    )  act, 0 py
FROM previousyearactual
WHERE 
    clientcode = ?1
    AND year = ?2
   
    union
    SELECT 'Profit And Loss Accounts'heading,
    'Profit Befor Tax' AS maingroup,0 bud,0 act,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Depreciation' then - amount
            
            ELSE 0
        END
    )  py
FROM previousyearactual
WHERE 
    clientcode = ?1
    AND year = ?3
    )t group by t.heading,t.maingroup
    union
    select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(SELECT 'Profit And Loss Accounts'heading,
    'Profit After Tax' AS maingroup,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Depreciation' then -amount
            when maingroup='Over Head' and subgroup='Income Tax' then -amount
            ELSE 0
        END
    ) AS bud,0 act, 0 py
FROM budget
WHERE 
    clientcode = ?1
    AND year = ?2
   union
    SELECT 'Profit And Loss Accounts'heading,
    'Profit After Tax' AS maingroup,0 bud,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Depreciation' then -amount
            when maingroup='Over Head' and subgroup='Income Tax' then -amount
            ELSE 0
        END
    )  act, 0 py
FROM previousyearactual
WHERE 
    clientcode = ?1
    AND year = ?2
    
    union
    SELECT 'Profit And Loss Accounts'heading,
    'Profit After Tax' AS maingroup,0 bud,0 act,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Depreciation' then - amount
            when maingroup='Over Head' and subgroup='Income Tax' then -amount
            
            ELSE 0
        END
    )  py
FROM previousyearactual
WHERE 
    clientcode = ?1
    AND year = ?3
    )t group by t.heading,t.maingroup
    union
    select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(SELECT 'Profit And Loss Accounts'heading,
    'Cash Profit' AS maingroup,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Over Head' and subgroup='Income Tax' then -amount
            when maingroup='Over Head' and subgroup ='CSR Activity Exp' then -amount
            ELSE 0
        END
    ) AS bud,0 act, 0 py
FROM budget
WHERE 
    clientcode = ?1
    AND year = ?2
   union
    SELECT 'Profit And Loss Accounts'heading,
    'Cash Profit' AS maingroup,0 bud,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Over Head' and subgroup='Income Tax' then -amount
            when maingroup='Over Head' and subgroup ='CSR Activity Exp' then -amount
            ELSE 0
        END
    )  act, 0 py
FROM previousyearactual
WHERE 
    clientcode = ?1
    AND year = ?2
   
    union
    SELECT 'Profit And Loss Accounts'heading,
    'Cash Profit' AS maingroup,0 bud,0 act,
    SUM(
        CASE 
            -- ✅ Operations Income logic
            WHEN maingroup = 'Sales' THEN amount
            WHEN maingroup = 'Other Income' AND subgroup = 'Other Operating Income' THEN amount
            WHEN maingroup = 'Purchase' THEN -amount
            WHEN maingroup = 'Opening Stock' THEN -amount
            WHEN maingroup = 'Closing Stock' THEN amount
            WHEN maingroup = 'Conversion Expenses' THEN -amount
            WHEN maingroup = 'Over Head' AND subgroup IN ('Manpower Expenses', 'Selling & DistExp', 'Overhead', 'IT Expenses') THEN -amount
            WHEN maingroup = 'Finance Expenses' AND subgroup = 'Bank Charges' THEN -amount
            
            -- ✅ NIncome logic
            WHEN maingroup = 'Other Income' AND subgroup = 'Non Operating Income' THEN amount
            WHEN maingroup = 'Finance Expenses' AND subgroup IN ('Interest on Term Loan', 'Working Capital Interest') THEN -amount
            when maingroup='Over Head' and subgroup='Income Tax' then -amount
            when maingroup='Over Head' and subgroup ='CSR Activity Exp' then -amount
            
            ELSE 0
        END
    )  py
FROM previousyearactual
WHERE 
    clientcode = ?1
    AND year = ?3
    )t group by t.heading,t.maingroup)o) i group by i.heading,maingroup
    
    
    
   