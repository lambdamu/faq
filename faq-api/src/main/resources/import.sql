INSERT INTO faq(id, question, answer) VALUES(1, 'What is the answer to the question about life, the universe and everything?', '42');
INSERT INTO faq(id, question, answer) VALUES(2, 'What is the question about life, the universe and everything?', 'Please come back to this section in a few million years.');
INSERT INTO faq(id, question, answer) VALUES(3, 'What is the name of our galaxy?', 'Milky Way');
SELECT setval('public.faq_seq', 3);

INSERT INTO tag(id, name) VALUES(1, 'Hitchhiker''s Guide to the Galaxy');
INSERT INTO tag(id, name) VALUES(2, 'astronomy');
INSERT INTO tag(id, name) VALUES(3, 'philosophy');
SELECT setval('public.tag_seq', 3);

INSERT INTO faq_tag(faq_id, tag_id) VALUES(1, 1);
INSERT INTO faq_tag(faq_id, tag_id) VALUES(1, 3);
INSERT INTO faq_tag(faq_id, tag_id) VALUES(2, 1);
INSERT INTO faq_tag(faq_id, tag_id) VALUES(3, 2);


