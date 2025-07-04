# 💰 Salary Calculation Logic Documentation

## Overview
The Employee Management System uses a comprehensive salary calculation formula that takes into account multiple components including basic salary, deductions, bonuses, overtime, and special allowances.

## 🔢 Updated Salary Calculation Formula

```java
Float totalSalary = (basicSalary - (basicSalary/25 * noPayDays)) + 
                    attendanceBonus + 
                    (otRate * overTimeHours) + 
                    specialAllowance;
```

## 📋 Components Breakdown

### 1. **Basic Salary Deductions**
- **Formula:** `basicSalary - (basicSalary/25 * noPayDays)`
- **Logic:** For each unpaid day, deduct 1/25th of the basic salary
- **Example:** If basic salary is $50,000 and employee has 2 unpaid days:
  - Deduction = $50,000 ÷ 25 × 2 = $4,000
  - Net basic salary = $50,000 - $4,000 = $46,000

### 2. **Attendance Bonus**
- **Formula:** `attendanceBonus` (fixed amount)
- **Logic:** Additional bonus for good attendance
- **Example:** $1,000 attendance bonus

### 3. **Overtime Pay**
- **Formula:** `otRate × overTimeHours`
- **Logic:** Hourly overtime rate multiplied by overtime hours worked
- **Example:** If OT rate is $25/hour and employee worked 10 OT hours:
  - Overtime pay = $25 × 10 = $250

### 4. **Special Allowance** ⭐ **NEW**
- **Formula:** `specialAllowance` (fixed amount)
- **Logic:** Additional fixed allowance from salary details
- **Example:** $5,000 special allowance

## 📊 Complete Example Calculation

```java
// Input values
Integer basicSalary = 50000;        // $50,000 basic salary
Float otRate = 25.0f;              // $25/hour overtime rate
Integer specialAllowance = 5000;    // $5,000 special allowance
Float noPayDays = 2.0f;            // 2 unpaid days
Integer attendanceBonus = 1000;     // $1,000 attendance bonus
Float overTimeHours = 10.0f;       // 10 overtime hours

// Calculation
Float totalSalary = (50000 - (50000/25 * 2)) + 1000 + (25 * 10) + 5000;
//                    (50000 - 4000)           + 1000 + 250     + 5000
//                    46000                     + 1000 + 250     + 5000
//                    = $52,250
```

## 🗄️ Database Tables Involved

### 1. **`salary_details` Table** (BasicSalary entity)
- `id` - Employee ID
- `basic_salary` - Base salary amount
- `ot_rate` - Overtime rate per hour
- `special_allowance` - Additional allowance ⭐
- `salary_date` - Date when salary was set

### 2. **`salary_data` Table** (SalaryData entity)
- `id` - Employee ID
- `date` - Pay period date
- `no_pay_days` - Number of unpaid days
- `over_time_hours` - Overtime hours worked
- `attendance_bonus` - Attendance bonus amount

### 3. **`salary` Table** (Salary entity)
- `id` - Employee ID
- `date` - Pay period date
- `salary_amount` - Final calculated salary

## 🔄 Salary Calculation Process

1. **Retrieve Basic Information:**
   - Get basic salary from `salary_details` table
   - Get overtime rate from `salary_details` table
   - Get special allowance from `salary_details` table ⭐

2. **Retrieve Monthly Data:**
   - Get attendance data from `salary_data` table for specific month
   - Extract: no-pay days, overtime hours, attendance bonus

3. **Calculate Final Salary:**
   - Apply the formula: `(Basic - Deductions) + Bonus + Overtime + Special Allowance`

## 📝 Business Rules

1. **Monthly Working Days:** Assumes 25 working days per month
2. **No-Pay Days:** Each unpaid day deducts 1/25th of basic salary
3. **Overtime:** Paid at the specified hourly rate
4. **Attendance Bonus:** Fixed amount for good attendance
5. **Special Allowance:** Fixed amount added to total salary ⭐

## 🧮 Calculation Examples

### Example 1: Full Attendance, No Overtime
```
Basic Salary: $50,000
Special Allowance: $5,000
No-Pay Days: 0
Attendance Bonus: $1,000
Overtime Hours: 0

Calculation: (50000 - 0) + 1000 + 0 + 5000 = $56,000
```

### Example 2: With Deductions and Overtime
```
Basic Salary: $50,000
Special Allowance: $5,000
No-Pay Days: 2
Attendance Bonus: $1,000
Overtime Hours: 10 (at $25/hour)

Calculation: (50000 - 4000) + 1000 + 250 + 5000 = $52,250
```

### Example 3: Maximum Deductions
```
Basic Salary: $50,000
Special Allowance: $5,000
No-Pay Days: 25 (full month absent)
Attendance Bonus: $0
Overtime Hours: 0

Calculation: (50000 - 50000) + 0 + 0 + 5000 = $5,000
```

## 🔧 Implementation Details

### Repository Methods
```java
// BasicSalaryRepository
Integer getBasicSalary(Integer id);
Float getOtRate(Integer id);
Integer getSpecialAllowance(Integer id);  // ⭐ NEW

// SalaryDataRepository
SalaryData getSalaryData(Integer id, Date date);
```

### Service Method
```java
public Float calculateSalary(Integer id, Date date){
    Integer basicSalary = getBasicSalary(id);
    Float otRate = getOtRate(id);
    Integer specialAllowance = getSpecialAllowance(id);  // ⭐ NEW
    SalaryData salaryData = getSalaryData(id,date);

    Float totalSalary = (basicSalary-(basicSalary/25 * salaryData.getNoPayDays())) + 
                       salaryData.getAttendanceBonus() + 
                       (otRate*salaryData.getOverTimeHours()) + 
                       specialAllowance;  // ⭐ NEW

    return totalSalary;
}
```

## ⚠️ Important Notes

1. **Special Allowance is Always Added:** Unlike other components, special allowance is not affected by attendance or deductions
2. **Data Validation:** All salary components must be positive numbers
3. **Date Requirements:** Salary date is required when creating salary records
4. **Admin Tracking:** All salary records are tracked with admin ID for accountability

## 🚀 Benefits of Including Special Allowance

1. **Comprehensive Compensation:** Now includes all salary components
2. **Flexible Allowances:** Supports various types of special allowances
3. **Accurate Calculations:** Reflects real-world salary structures
4. **Better Reporting:** Complete salary breakdown for analysis 