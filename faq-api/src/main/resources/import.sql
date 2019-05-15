INSERT INTO faq(id, question, answer) VALUES(1, 'What is the answer to the question about life, the universe and everything?', '42');
INSERT INTO faq(id, question, answer) VALUES(2, 'What is the question about life, the universe and everything?', 'Please come back to this section in a few million years.');
INSERT INTO faq(id, question, answer) VALUES(3, 'What is the name of our galaxy?', 'Milky Way');
INSERT INTO faq(id, question, answer) VALUES(4, 'How old is the universe?', 'The universe is about 13.8 billion years old.');
INSERT INTO faq(id, question, answer) VALUES(5, 'Why is the sky blue?', 'Blue light is scattered in all directions by the tiny molecules of air in Earth''s atmosphere. Blue is scattered more than other colors because it travels as shorter, smaller waves. This is why we see a blue sky most of the time.');
INSERT INTO faq(id, question, answer) VALUES(6, 'What is the closest galaxy', 'Andromeda Galaxy is the closest spiral galaxy, about 2 million light years away from the Milky Way.');
SELECT setval('public.faq_seq', 6);

INSERT INTO tag(id, name) VALUES(1, 'Hitchhiker''s Guide to the Galaxy');
INSERT INTO tag(id, name) VALUES(2, 'astronomy');
INSERT INTO tag(id, name) VALUES(3, 'philosophy');
SELECT setval('public.tag_seq', 3);

INSERT INTO faq_tag(faq_id, tag_id) VALUES(1, 1);
INSERT INTO faq_tag(faq_id, tag_id) VALUES(1, 3);
INSERT INTO faq_tag(faq_id, tag_id) VALUES(2, 1);
INSERT INTO faq_tag(faq_id, tag_id) VALUES(3, 2);
INSERT INTO faq_tag(faq_id, tag_id) VALUES(4, 2);
INSERT INTO faq_tag(faq_id, tag_id) VALUES(5, 2);
INSERT INTO faq_tag(faq_id, tag_id) VALUES(6, 2);


