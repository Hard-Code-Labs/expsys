CREATE OR REPLACE PROCEDURE exp.createCategoriesByNewPerson(IN p_perId NUMERIC)
    LANGUAGE SQL
    BEGIN ATOMIC
-- Common categories: [food, education (Expense)] - [salary (Income)]
    INSERT INTO exp.exp_person_category
        (cat_name, cat_type,cat_icon, per_id, created_at,modified_at, is_delete)
    VALUES
        ('Food', 'E','🍲', p_perId, CURRENT_TIMESTAMP,null, false),
        ('Education', 'E','📚', p_perId, CURRENT_TIMESTAMP,null, false),
        ('Salary', 'I','🤑', p_perId, CURRENT_TIMESTAMP,null, false);
    END;


