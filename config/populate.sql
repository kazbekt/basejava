INSERT INTO resume (uuid, full_name)
VALUES ('7de882da-02f2-4d16-8daa-60660aaf4071', 'Name1'),
       ('a97b3ac3-3817-4c3f-8a5f-178497311f1d', 'Name2');

INSERT INTO contact (type, value, resume_uuid)
VALUES ('SKYPE', 'skypeg', '7de882da-02f2-4d16-8daa-60660aaf4071'),
       ('SKYPE', 'skypeg', 'a97b3ac3-3817-4c3f-8a5f-178497311f1d'),
       ('PHONE', '434343534', '7de882da-02f2-4d16-8daa-60660aaf4071'),
       ('PHONE', '434343534', 'a97b3ac3-3817-4c3f-8a5f-178497311f1d'),
       ('EMAIL', 'skypeg', '7de882da-02f2-4d16-8daa-60660aaf4071'),
       ('EMAIL', 'skypeg', 'a97b3ac3-3817-4c3f-8a5f-178497311f1d'),
       ('LINKEDIN', '434343534', '7de882da-02f2-4d16-8daa-60660aaf4071'),
       ('LINKEDIN', '434343534', 'a97b3ac3-3817-4c3f-8a5f-178497311f1d'),
       ('GITHUB', 'skypeg', '7de882da-02f2-4d16-8daa-60660aaf4071'),
       ('GITHUB', 'skypeg', 'a97b3ac3-3817-4c3f-8a5f-178497311f1d'),
       ('STACKOVERFLOW', '434343534', '7de882da-02f2-4d16-8daa-60660aaf4071'),
       ('STACKOVERFLOW', '434343534', 'a97b3ac3-3817-4c3f-8a5f-178497311f1d'),
       ('HOMEPAGE', 'skypeg', '7de882da-02f2-4d16-8daa-60660aaf4071'),
       ('HOMEPAGE', 'skypeg', 'a97b3ac3-3817-4c3f-8a5f-178497311f1d');

INSERT INTO text_section (ts_type, ts_value, ts_resume_uuid)
VALUES ('PERSONAL', 'Tatatata', '7de882da-02f2-4d16-8daa-60660aaf4071'),
       ('OBJECTIVE', 'Blabla', 'a97b3ac3-3817-4c3f-8a5f-178497311f1d'),
       ('PERSONAL', 'Tatatata', 'a97b3ac3-3817-4c3f-8a5f-178497311f1d'),
       ('OBJECTIVE', 'Blabla', '7de882da-02f2-4d16-8daa-60660aaf4071');

INSERT INTO list_section (ls_type, ls_value, ls_resume_uuid)
VALUES ('ACHIEVEMENT', 'AchiAchi\nAchAch\nAcAcAc', '7de882da-02f2-4d16-8daa-60660aaf4071'),
       ('QUALIFICATIONS', 'First\nSectond', 'a97b3ac3-3817-4c3f-8a5f-178497311f1d'),
('ACHIEVEMENT', 'AchiAchi\nAchAch\nAcAcAc', 'a97b3ac3-3817-4c3f-8a5f-178497311f1d'),
         ('QUALIFICATIONS', 'First\nSectond', '7de882da-02f2-4d16-8daa-60660aaf4071');