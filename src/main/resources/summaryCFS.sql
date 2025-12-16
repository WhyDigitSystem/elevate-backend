select row_number() Over() sno,i.heading,i.maingroup,sum(i.bud)bud,sum(i.act)act,sum(i.py)py,sum(i.ybud)ybud,sum(i.yact)yact,sum(i.ypy)ypy,sum(i.fbud)fbud,((sum(i.yact)/( select monthsequence from previousyearactual where year=?2 and month=?4 and clientcode=?1 group by monthsequence))*12)estimate,sum(i.fpy)fpy from(
select a.heading,a.maingroup,a.bud,a.act,a.py,0 ybud,0 yact,0 ypy,0 fbud,0 estimate,0 fpy from(
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(
select 'Cash Flow Statment'heading, 'Total Operating Inflow' maingroup,sum(amount)bud,0 act, 0 py from budget where clientcode=?1 and year=?2 and month=?4 and maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') union
select 'Cash Flow Statment'heading, 'Total Operating Inflow' maingroup,0 bud,sum(amount)act, 0 py from previousyearactual where clientcode=?1 and year=?2 and month=?4 and maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') union
select 'Cash Flow Statment'heading, 'Total Operating Inflow' maingroup,0 bud, 0 act,sum(amount) py from previousyearactual where clientcode=?1 and year=?3 and month=?4 and maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow')) t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(select 'Cash Flow Statment'heading, 'Total Operating Outflow' maingroup,sum(amount) bud,0 act, 0 py from budget where clientcode=?1 and year=?2 and month=?4 and maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow')
union
select 'Cash Flow Statment'heading, 'Total Operating Outflow' maingroup,0 bud,sum(amount) act, 0 py from previousyearactual where clientcode=?1 and year=?2 and month=?4 and maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow')
union
select 'Cash Flow Statment'heading, 'Total Operating Outflow' maingroup,0 bud,0 act, sum(amount) py from previousyearactual where clientcode=?1 and year=?3 and month=?4 and maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') 
)t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(select 'Cash Flow Statment'heading, 'Total Operating  Surplus/ Deficit' maingroup,
sum( case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount else 0 end) bud,0 act, 0 py from budget where clientcode=?1 and year=?2 and month=?4  union
select 'Cash Flow Statment'heading, 'Total Operating  Surplus/ Deficit' maingroup,0 bud,sum( case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount else 0 end)act, 0 py from previousyearactual where clientcode=?1 and year=?2 and month=?4 union
select 'Cash Flow Statment'heading, 'Total Operating  Surplus/ Deficit' maingroup,0 bud, 0 act,sum( case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount else 0 end) py from previousyearactual where clientcode=?1 and year=?3 and month=?4
)t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(
select 'Cash Flow Statment'heading, 'Total Non Operating Inflow' maingroup,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount else 0 end)bud,0 act, 0 py from budget where clientcode=?1 and year=?2 and month=?4 union
select 'Cash Flow Statment'heading, 'Total Non Operating Inflow' maingroup,0 bud,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount else 0 end)act, 0 py from previousyearactual where clientcode=?1 and year=?2 and month=?4  union
select 'Cash Flow Statment'heading, 'Total Non Operating Inflow' maingroup,0 bud, 0 act,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount else 0 end) py from previousyearactual where clientcode=?1 and year=?3 and month=?4 ) t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(
select 'Cash Flow Statment'heading, 'Total Non Operating Outflow' maingroup,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then  amount else 0 end)bud,0 act, 0 py from budget where clientcode=?1 and year=?2 and month=?4 union
select 'Cash Flow Statment'heading, 'Total Non Operating Outflow' maingroup,0 bud,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then  amount else 0 end)act, 0 py from previousyearactual where clientcode=?1 and year=?2 and month=?4  union
select 'Cash Flow Statment'heading, 'Total Non Operating Outflow' maingroup,0 bud, 0 act,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then  amount else 0 end) py from previousyearactual where clientcode=?1 and year=?3 and month=?4 ) t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(
select 'Cash Flow Statment'heading, 'Total Non Operating Surplus / Deficit' maingroup,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount else 0 end)bud,0 act, 0 py from budget where clientcode=?1 and year=?2 and month=?4 union
select 'Cash Flow Statment'heading, 'Total Non Operating Surplus / Deficit' maingroup,0 bud,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount else 0 end)act, 0 py from previousyearactual where clientcode=?1 and year=?2 and month=?4  union
select 'Cash Flow Statment'heading, 'Total Non Operating Surplus / Deficit' maingroup,0 bud, 0 act,sum(  case when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount else 0 end) py from previousyearactual where clientcode=?1 and year=?3 and month=?4 ) t group by t.heading,t.maingroup union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(
select 'Cash Flow Statment'heading, 'Total Net Surplus / Deficit' maingroup,sum(case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount else 0 end)bud,0 act, 0 py from budget where clientcode=?1 and year=?2 and month=?4 union
select 'Cash Flow Statment'heading, 'Total Net Surplus / Deficit' maingroup,0 bud,sum(case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount else 0 end)act, 0 py from previousyearactual where clientcode=?1 and year=?2 and month=?4  union
select 'Cash Flow Statment'heading, 'Total Net Surplus / Deficit' maingroup,0 bud, 0 act,sum(case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount else 0 end) py from previousyearactual where clientcode=?1 and year=?3 and month=?4 ) t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(
select 'Cash Flow Statment'heading, 'Opening Balance' maingroup,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname in('Opening Balance') then amount else 0 end)bud,0 act, 0 py from budget where clientcode=?1 and year=?2 and month=?4 union
select 'Cash Flow Statment'heading, 'Opening Balance' maingroup,0 bud,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname in('Opening Balance') then amount else 0 end)act, 0 py from previousyearactual where clientcode=?1 and year=?2 and month=?4  union
select 'Cash Flow Statment'heading, 'Opening Balance' maingroup,0 bud, 0 act,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname in('Opening Balance') then amount else 0 end) py from previousyearactual where clientcode=?1 and year=?3 and month=?4 ) t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(
select 'Cash Flow Statment'heading, 'Closing Balance' maingroup,sum(case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount
when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname in('Opening Balance') then amount else 0 end)bud,0 act, 0 py from budget where clientcode=?1 and year=?2 and month=?4 union
select 'Cash Flow Statment'heading, 'Closing Balance' maingroup,0 bud,sum(case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount
when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname in('Opening Balance') then amount else 0 end)act, 0 py from previousyearactual where clientcode=?1 and year=?2 and month=?4  union
select 'Cash Flow Statment'heading, 'Closing Balance' maingroup,0 bud, 0 act,sum(case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount
when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname in('Opening Balance') then amount else 0 end) py from previousyearactual where clientcode=?1 and year=?3 and month=?4 ) t group by t.heading,t.maingroup) a 
union
select u.heading,u.maingroup,0 bud, 0 act,0 py,u.ybud,u.yact,u.ypy,0 fbud,0 estimate,0 fpy from(
select s.heading,s.maingroup,sum(s.bud)ybud,sum(s.act)yact,sum(s.py)ypy from(
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(
select 'Cash Flow Statment'heading, 'Total Operating Inflow' maingroup,sum(amount)bud,0 act, 0 py from budget where clientcode=?1 and year=?2 AND  monthsequence <=( select monthsequence from budget  where year=?2 and month=?4 and clientcode=?1 group by monthsequence) and maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') union
select 'Cash Flow Statment'heading, 'Total Operating Inflow' maingroup,0 bud,sum(amount)act, 0 py from previousyearactual where clientcode=?1 and year=?2 AND  monthsequence <=( select monthsequence from previousyearactual  where year=?2 and month=?4 and clientcode=?1 group by monthsequence) and maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') union
select 'Cash Flow Statment'heading, 'Total Operating Inflow' maingroup,0 bud, 0 act,sum(amount) py from previousyearactual where clientcode=?1 and year=?3 AND  monthsequence <=( select monthsequence from previousyearactual  where year=?3 and month=?4 and clientcode=?1 group by monthsequence) and maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow')) t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(select 'Cash Flow Statment'heading, 'Total Operating Outflow' maingroup,sum(amount) bud,0 act, 0 py from budget where clientcode=?1 and year=?2 AND  monthsequence <=( select monthsequence from budget  where year=?2 and month=?4 and clientcode=?1 group by monthsequence) and maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow')
union
select 'Cash Flow Statment'heading, 'Total Operating Outflow' maingroup,0 bud,sum(amount) act, 0 py from previousyearactual where clientcode=?1 and year=?2 AND  monthsequence <=( select monthsequence from previousyearactual  where year=?2 and month=?4 and clientcode=?1 group by monthsequence) and maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow')
union
select 'Cash Flow Statment'heading, 'Total Operating Outflow' maingroup,0 bud,0 act, sum(amount) py from previousyearactual where clientcode=?1 and year=?3 AND  monthsequence <=( select monthsequence from previousyearactual  where year=?3 and month=?4 and clientcode=?1 group by monthsequence) and maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') 
)t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(select 'Cash Flow Statment'heading, 'Total Operating  Surplus/ Deficit' maingroup,
sum( case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount else 0 end) bud,0 act, 0 py from budget where clientcode=?1 and year=?2 AND  monthsequence <=( select monthsequence from budget  where year=?2 and month=?4 and clientcode=?1 group by monthsequence) union
select 'Cash Flow Statment'heading, 'Total Operating  Surplus/ Deficit' maingroup,0 bud,sum( case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount else 0 end)act, 0 py from previousyearactual where clientcode=?1 and year=?2 AND  monthsequence <=( select monthsequence from previousyearactual  where year=?2 and month=?4 and clientcode=?1 group by monthsequence) union
select 'Cash Flow Statment'heading, 'Total Operating  Surplus/ Deficit' maingroup,0 bud, 0 act,sum( case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount else 0 end) py from previousyearactual where clientcode=?1 and year=?3 AND  monthsequence <=( select monthsequence from previousyearactual  where year=?3 and month=?4 and clientcode=?1 group by monthsequence)
)t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(
select 'Cash Flow Statment'heading, 'Total Non Operating Inflow' maingroup,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount else 0 end)bud,0 act, 0 py from budget where clientcode=?1 and year=?2 AND  monthsequence <=( select monthsequence from budget  where year=?2 and month=?4 and clientcode=?1 group by monthsequence) union
select 'Cash Flow Statment'heading, 'Total Non Operating Inflow' maingroup,0 bud,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount else 0 end)act, 0 py from previousyearactual where clientcode=?1 and year=?2 AND  monthsequence <=( select monthsequence from previousyearactual  where year=?2 and month=?4 and clientcode=?1 group by monthsequence)  union
select 'Cash Flow Statment'heading, 'Total Non Operating Inflow' maingroup,0 bud, 0 act,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount else 0 end) py from previousyearactual where clientcode=?1 and year=?3 AND  monthsequence <=( select monthsequence from previousyearactual  where year=?3 and month=?4 and clientcode=?1 group by monthsequence) ) t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(
select 'Cash Flow Statment'heading, 'Total Non Operating Outflow' maingroup,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then  amount else 0 end)bud,0 act, 0 py from budget where clientcode=?1 and year=?2 AND  monthsequence <=( select monthsequence from budget  where year=?2 and month=?4 and clientcode=?1 group by monthsequence) union
select 'Cash Flow Statment'heading, 'Total Non Operating Outflow' maingroup,0 bud,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then  amount else 0 end)act, 0 py from previousyearactual where clientcode=?1 and year=?2 AND  monthsequence <=( select monthsequence from previousyearactual  where year=?2 and month=?4 and clientcode=?1 group by monthsequence)  union
select 'Cash Flow Statment'heading, 'Total Non Operating Outflow' maingroup,0 bud, 0 act,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then  amount else 0 end) py from previousyearactual where clientcode=?1 and year=?3 AND  monthsequence <=( select monthsequence from previousyearactual  where year=?3 and month=?4 and clientcode=?1 group by monthsequence) ) t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(
select 'Cash Flow Statment'heading, 'Total Non Operating Surplus / Deficit' maingroup,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount else 0 end)bud,0 act, 0 py from budget where clientcode=?1 and year=?2 AND  monthsequence <=( select monthsequence from budget  where year=?2 and month=?4 and clientcode=?1 group by monthsequence) union
select 'Cash Flow Statment'heading, 'Total Non Operating Surplus / Deficit' maingroup,0 bud,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount else 0 end)act, 0 py from previousyearactual where clientcode=?1 and year=?2 AND  monthsequence <=( select monthsequence from previousyearactual  where year=?2 and month=?4 and clientcode=?1 group by monthsequence) union
select 'Cash Flow Statment'heading, 'Total Non Operating Surplus / Deficit' maingroup,0 bud, 0 act,sum(  case when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount else 0 end) py from previousyearactual where clientcode=?1 and year=?3 AND  monthsequence <=( select monthsequence from previousyearactual  where year=?3 and month=?4 and clientcode=?1 group by monthsequence) ) t group by t.heading,t.maingroup union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(
select 'Cash Flow Statment'heading, 'Total Net Surplus / Deficit' maingroup,sum(case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount else 0 end)bud,0 act, 0 py from budget where clientcode=?1 and year=?2 AND  monthsequence <=( select monthsequence from budget  where year=?2 and month=?4 and clientcode=?1 group by monthsequence) union
select 'Cash Flow Statment'heading, 'Total Net Surplus / Deficit' maingroup,0 bud,sum(case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount else 0 end)act, 0 py from previousyearactual where clientcode=?1 and year=?2 AND  monthsequence <=( select monthsequence from previousyearactual  where year=?2 and month=?4 and clientcode=?1 group by monthsequence)  union
select 'Cash Flow Statment'heading, 'Total Net Surplus / Deficit' maingroup,0 bud, 0 act,sum(case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount else 0 end) py from previousyearactual where clientcode=?1 and year=?3 AND  monthsequence <=( select monthsequence from previousyearactual  where year=?3 and month=?4 and clientcode=?1 group by monthsequence) ) t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(
select 'Cash Flow Statment'heading, 'Opening Balance' maingroup,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname in('Opening Balance') then amount else 0 end)bud,0 act, 0 py from budget where clientcode=?1 and year=?2 AND  monthsequence <=( select monthsequence from budget  where year=?2 and month=?4 and clientcode=?1 group by monthsequence) union
select 'Cash Flow Statment'heading, 'Opening Balance' maingroup,0 bud,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname in('Opening Balance') then amount else 0 end)act, 0 py from previousyearactual where clientcode=?1 and year=?2 AND  monthsequence <=( select monthsequence from previousyearactual  where year=?2 and month=?4 and clientcode=?1 group by monthsequence)  union
select 'Cash Flow Statment'heading, 'Opening Balance' maingroup,0 bud, 0 act,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname in('Opening Balance') then amount else 0 end) py from previousyearactual where clientcode=?1 and year=?3 AND  monthsequence <=( select monthsequence from previousyearactual  where year=?3 and month=?4 and clientcode=?1 group by monthsequence) ) t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(
select 'Cash Flow Statment'heading, 'Closing Balance' maingroup,sum(case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount
when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname in('Opening Balance') then amount else 0 end)bud,0 act, 0 py from budget where clientcode=?1 and year=?2 AND  monthsequence <=( select monthsequence from budget  where year=?2 and month=?4 and clientcode=?1 group by monthsequence) union
select 'Cash Flow Statment'heading, 'Closing Balance' maingroup,0 bud,sum(case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount
when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname in('Opening Balance') then amount else 0 end)act, 0 py from previousyearactual where clientcode=?1 and year=?2 AND  monthsequence <=( select monthsequence from previousyearactual  where year=?2 and month=?4 and clientcode=?1 group by monthsequence)  union
select 'Cash Flow Statment'heading, 'Closing Balance' maingroup,0 bud, 0 act,sum(case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount
when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname in('Opening Balance') then amount else 0 end) py from previousyearactual where clientcode=?1 and year=?3 AND  monthsequence <=( select monthsequence from previousyearactual  where year=?3 and month=?4 and clientcode=?1 group by monthsequence) ) t group by t.heading,t.maingroup)s group by s.heading, s.maingroup )u group by u.heading,u.maingroup
union
select o.heading,o.maingroup,0 bud, 0 act,0 py,0 ybud,0 yact,0 ypy,o.fbud,0 estimate,o.fpy from(
select t.heading,t.maingroup,sum(t.bud)fbud,sum(t.act)estimate,sum(t.py)fpy from(
select 'Cash Flow Statment'heading, 'Total Operating Inflow' maingroup,sum(amount)bud,0 act, 0 py from budget where clientcode=?1 and year=?2  and maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') union
select 'Cash Flow Statment'heading, 'Total Operating Inflow' maingroup,0 bud,sum(amount)act, 0 py from previousyearactual where clientcode=?1 and year=?2  and maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') union
select 'Cash Flow Statment'heading, 'Total Operating Inflow' maingroup,0 bud, 0 act,sum(amount) py from previousyearactual where clientcode=?1 and year=?3  and maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow')) t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(select 'Cash Flow Statment'heading, 'Total Operating Outflow' maingroup,sum(amount) bud,0 act, 0 py from budget where clientcode=?1 and year=?2  and maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow')
union
select 'Cash Flow Statment'heading, 'Total Operating Outflow' maingroup,0 bud,sum(amount) act, 0 py from previousyearactual where clientcode=?1 and year=?2  and maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow')
union
select 'Cash Flow Statment'heading, 'Total Operating Outflow' maingroup,0 bud,0 act, sum(amount) py from previousyearactual where clientcode=?1 and year=?3  and maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') 
)t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(select 'Cash Flow Statment'heading, 'Total Operating  Surplus/ Deficit' maingroup,
sum( case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount else 0 end) bud,0 act, 0 py from budget where clientcode=?1 and year=?2  union
select 'Cash Flow Statment'heading, 'Total Operating  Surplus/ Deficit' maingroup,0 bud,sum( case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount else 0 end)act, 0 py from previousyearactual where clientcode=?1 and year=?2  union
select 'Cash Flow Statment'heading, 'Total Operating  Surplus/ Deficit' maingroup,0 bud, 0 act,sum( case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount else 0 end) py from previousyearactual where clientcode=?1 and year=?3 
)t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(
select 'Cash Flow Statment'heading, 'Total Non Operating Inflow' maingroup,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount else 0 end)bud,0 act, 0 py from budget where clientcode=?1 and year=?2  union
select 'Cash Flow Statment'heading, 'Total Non Operating Inflow' maingroup,0 bud,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount else 0 end)act, 0 py from previousyearactual where clientcode=?1 and year=?2   union
select 'Cash Flow Statment'heading, 'Total Non Operating Inflow' maingroup,0 bud, 0 act,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount else 0 end) py from previousyearactual where clientcode=?1 and year=?3  ) t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(
select 'Cash Flow Statment'heading, 'Total Non Operating Outflow' maingroup,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then  amount else 0 end)bud,0 act, 0 py from budget where clientcode=?1 and year=?2  union
select 'Cash Flow Statment'heading, 'Total Non Operating Outflow' maingroup,0 bud,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then  amount else 0 end)act, 0 py from previousyearactual where clientcode=?1 and year=?2   union
select 'Cash Flow Statment'heading, 'Total Non Operating Outflow' maingroup,0 bud, 0 act,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then  amount else 0 end) py from previousyearactual where clientcode=?1 and year=?3  ) t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(
select 'Cash Flow Statment'heading, 'Total Non Operating Surplus / Deficit' maingroup,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount else 0 end)bud,0 act, 0 py from budget where clientcode=?1 and year=?2  union
select 'Cash Flow Statment'heading, 'Total Non Operating Surplus / Deficit' maingroup,0 bud,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount else 0 end)act, 0 py from previousyearactual where clientcode=?1 and year=?2  union
select 'Cash Flow Statment'heading, 'Total Non Operating Surplus / Deficit' maingroup,0 bud, 0 act,sum(  case when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount else 0 end) py from previousyearactual where clientcode=?1 and year=?3  ) t group by t.heading,t.maingroup union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(
select 'Cash Flow Statment'heading, 'Total Net Surplus / Deficit' maingroup,sum(case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount else 0 end)bud,0 act, 0 py from budget where clientcode=?1 and year=?2  union
select 'Cash Flow Statment'heading, 'Total Net Surplus / Deficit' maingroup,0 bud,sum(case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount else 0 end)act, 0 py from previousyearactual where clientcode=?1 and year=?2   union
select 'Cash Flow Statment'heading, 'Total Net Surplus / Deficit' maingroup,0 bud, 0 act,sum(case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount else 0 end) py from previousyearactual where clientcode=?1 and year=?3  ) t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(
select 'Cash Flow Statment'heading, 'Opening Balance' maingroup,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname in('Opening Balance') then amount else 0 end)bud,0 act, 0 py from budget where clientcode=?1 and year=?2  union
select 'Cash Flow Statment'heading, 'Opening Balance' maingroup,0 bud,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname in('Opening Balance') then amount else 0 end)act, 0 py from previousyearactual where clientcode=?1 and year=?2   union
select 'Cash Flow Statment'heading, 'Opening Balance' maingroup,0 bud, 0 act,sum( case when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname in('Opening Balance') then amount else 0 end) py from previousyearactual where clientcode=?1 and year=?3  ) t group by t.heading,t.maingroup
union
select t.heading,t.maingroup,sum(t.bud)bud,sum(t.act)act,sum(t.py)py from(
select 'Cash Flow Statment'heading, 'Closing Balance' maingroup,sum(case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount
when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname in('Opening Balance') then amount else 0 end)bud,0 act, 0 py from budget where clientcode=?1 and year=?2  union
select 'Cash Flow Statment'heading, 'Closing Balance' maingroup,0 bud,sum(case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount
when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname in('Opening Balance') then amount else 0 end)act, 0 py from previousyearactual where clientcode=?1 and year=?2   union
select 'Cash Flow Statment'heading, 'Closing Balance' maingroup,0 bud, 0 act,sum(case when maingroup='Cash Flow Statement' and subgroup='Operational Inflow' and accountname not in('Total Operational Inflow') then amount
			when maingroup='Cash Flow Statement' and subgroup='Other Operating Income' and accountname not in('Total Other Operating Income','Total Operational Inflow and Other Operating Income') then amount
            when maingroup='Cash Flow Statement' and subgroup in('Sundry Creditors','Statutory Liability','Conversion Exp','ManPower Expenses','Selling & DistExp','Overhead','IT Expenses','Working Capital Interest','Bank Charges','Interest on Term Loan')
and accountname not in('Total Creditors payment','Total Statutory Payment','Total Conversion Payments','Total Man Power Expenses','Total Selling & Distribution Expenses Paid','Total Over Heads','Total IT Expenses','Total Finance Expenses','Total Bank Charges','Total Interest On Term','Total Operational Outflow','Total Surplus Deficit Operational Cash flow') then - amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operating Inflow' and accountname not in('Total Non Operational Inflow') then  amount
 when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname not in('Total Non Operational Outflow',
'Total Surplus Deficit Non Operational Cash flow',
'Total Net Surplus Deficit',
'Opening Balance',
'Closing Balance',
'Closing Bal as per B/S',
'Total Difference') then - amount
when maingroup='Cash Flow Statement' and subgroup='Non Operational Outflow' and accountname in('Opening Balance') then amount else 0 end) py from previousyearactual where clientcode=?1 and year=?3  ) t group by t.heading,t.maingroup)o)i group by i.heading,i.maingroup;