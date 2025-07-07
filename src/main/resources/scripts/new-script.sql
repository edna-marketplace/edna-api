INSERT INTO public.customer (
    id, created_at, email, "password", phone, cpf, name, deleted
) VALUES
      ('d290f1ee-6c54-4b01-90e6-d701748f0851', CURRENT_TIMESTAMP, 'joao.silva@example.com', 'password123', '48987654321', '48750168088', 'João Silva', false),
      ('2a6fa6dd-8b1f-4d3d-97cb-20adcf11cc74', CURRENT_TIMESTAMP, 'maria.oliveira@example.com', 'password456', '48987654322', '82416611003', 'Maria Oliveira', false),
      ('3b9f0b7c-a7e2-47c5-bc88-9f2522d6e78e', CURRENT_TIMESTAMP, 'pedro.souza@example.com', 'password789', '48987654323', '87024830093', 'Pedro Souza', false),
      ('4c8e1d08-5ae7-4b66-8c6b-563d44f872ed', CURRENT_TIMESTAMP, 'ana.costa@example.com', 'password101', '48987654324', '94448025071', 'Ana Costa', false),
      ('5d7d9e6f-8c4d-4c32-b0d5-372f9eb7fd43', CURRENT_TIMESTAMP, 'carla.pereira@example.com', 'password202', '48987654325', '09394763040', 'Carla Pereira', false),
      ('6e8f7g9h-9d5e-4d43-c1f6-483f0fc8ge54', CURRENT_TIMESTAMP, 'bruno.santos@example.com', 'password303', '48987654326', '12345678901', 'Bruno Santos', false),
      ('7f9g8h0i-0e6f-4e54-d2g7-594g1gd9hf65', CURRENT_TIMESTAMP, 'lucia.martins@example.com', 'password404', '48987654327', '23456789012', 'Lucia Martins', false),
      ('8g0h9i1j-1f7g-4f65-e3h8-605h2he0ig76', CURRENT_TIMESTAMP, 'carlos.rodrigues@example.com', 'password505', '48987654328', '34567890123', 'Carlos Rodrigues', false);

INSERT INTO public.store (
    id, created_at, email, "password", phone, cnpj, name, target_customer, deleted, stripe_onboarding_completed, stripe_account_id
) values
      ('8ca95f23-217b-485e-bdfa-35fa862b925c', CURRENT_TIMESTAMP, 'matheusbarcon@gmail.com', '$2a$10$Ld4SE26fH.N8FT2D8lQ7zeeJeY0ljqa1P0qV3geu7moMR5loxN9FW', '48991518490', '15173883000109', 'Brecho da Edna', 'ALL', false, true, 'acct_1RgwKnGdLdXJI2Hs'),
      -- FEMALE stores (4)
      ('6e1f8c1e-d229-4e98-99c2-7a6239a8b531', CURRENT_TIMESTAMP, 'brecho.bella@example.com', 'securepass1', '48976543210', '87736276000110', 'Brechó Bella', 'FEMALE', false, true, null),
      ('8a3d0b3f-ce6c-4b18-b1d9-9d3456d9e743', CURRENT_TIMESTAMP, 'chic.retro@example.com', 'securepass3', '48976543212', '84577905000119', 'Chic Retrô', 'FEMALE', false, true, null),
      ('a1b2c3d4-e5f6-4g7h-i8j9-k0l1m2n3o4p5', CURRENT_TIMESTAMP, 'fashion.feminina@example.com', 'securepass6', '48976543215', '11223344000155', 'Fashion Feminina', 'FEMALE', false, true, null),
      ('b2c3d4e5-f6g7-4h8i-j9k0-l1m2n3o4p5q6', CURRENT_TIMESTAMP, 'estilo.mulher@example.com', 'securepass7', '48976543216', '22334455000166', 'Estilo Mulher', 'FEMALE', false, true, null),
      -- MALE stores (4)
      ('9b4e1c4f-de7d-4c29-c2e0-0e4567d0f854', CURRENT_TIMESTAMP, 'urban.wear@example.com', 'securepass4', '48976543213', '57556282000125', 'Urban Wear', 'MALE', false, true, null),
      ('c3d4e5f6-g7h8-4i9j-k0l1-m2n3o4p5q6r7', CURRENT_TIMESTAMP, 'masculino.style@example.com', 'securepass8', '48976543217', '33445566000177', 'Masculino Style', 'MALE', false, true, null),
      ('d4e5f6g7-h8i9-4j0k-l1m2-n3o4p5q6r7s8', CURRENT_TIMESTAMP, 'homem.moderno@example.com', 'securepass9', '48976543218', '44556677000188', 'Homem Moderno', 'MALE', false, true, null),
      ('e5f6g7h8-i9j0-4k1l-m2n3-o4p5q6r7s8t9', CURRENT_TIMESTAMP, 'brecho.masculino@example.com', 'securepass10', '48976543219', '55667788000199', 'Brechó Masculino', 'MALE', false, true, null),
      -- ALL stores (3)
      ('7f2c9a2e-bd5b-4a09-a3b8-8c2347c8d632', CURRENT_TIMESTAMP, 'vintage.style@example.com', 'securepass2', '48976543211', '57768821000190', 'Vintage Style', 'ALL', false, true, null),
      ('af5f2d5f-ef8e-4d39-d3f1-1f5678e1f965', CURRENT_TIMESTAMP, 'eco.brecho@example.com', 'securepass5', '48976543214', '12872344000170', 'Eco Brechó', 'ALL', false, true, null);


INSERT INTO public.store_image (id, created_at, deleted, url, type, store_id) VALUES
-- Multiple images
('s-img-01', CURRENT_TIMESTAMP, false, 'mock-female-store.png', 'PROFILE', '6e1f8c1e-d229-4e98-99c2-7a6239a8b531'),
('s-img-02', CURRENT_TIMESTAMP, false, 'mock-male-store.png', 'PROFILE', '9b4e1c4f-de7d-4c29-c2e0-0e4567d0f854'),
('s-img-03', CURRENT_TIMESTAMP, false, 'mock-all-store.png', 'PROFILE', '7f2c9a2e-bd5b-4a09-a3b8-8c2347c8d632');

INSERT INTO public.clothe (
    id, brand, brand_other, category, category_other, color, created_at, description, fabric, gender, "name", price_in_cents, "size", size_other, store_id, deleted, ordered
) VALUES
      -- T_SHIRT clothes (12 total)
      ('c1a2b3c4-d123-4abc-8e9f-a123b4c567d1', 'NIKE', NULL, 'T_SHIRT', NULL, 'vermelho', CURRENT_TIMESTAMP, 'Camiseta esportiva', 'algodão', 'MALE', 'Camiseta Nike', 12000, 'M', NULL, '6e1f8c1e-d229-4e98-99c2-7a6239a8b531', false, false),
      ('c4d5e6f7-e123-4def-9g1h-a234b5c678d2', 'NIKE', NULL, 'T_SHIRT', NULL, 'preto', CURRENT_TIMESTAMP, 'Camiseta casual preta', 'algodão', 'MALE', 'Camiseta Preta Nike', 11000, 'L', NULL, '6e1f8c1e-d229-4e98-99c2-7a6239a8b531', false, false),
      ('d6g7h8i9-j678-9ijk-4l6m-f789g0h123i7', 'CEA', NULL, 'T_SHIRT', NULL, 'laranja', CURRENT_TIMESTAMP, 'Camiseta retrô', 'algodão', 'UNISEX', 'Camiseta Laranja CEA', 12000, 'S', NULL, '7f2c9a2e-bd5b-4a09-a3b8-8c2347c8d632', false, false),
      ('e2g3h4i5-l890-1hij-f56m-h890i1j234k8', 'OTHER', 'Grife Exclusiva', 'T_SHIRT', NULL, 'rosa', CURRENT_TIMESTAMP, 'Camiseta fashion', 'seda', 'FEMALE', 'Camiseta Rosa', 14000, 'S', NULL, '8a3d0b3f-ce6c-4b18-b1d9-9d3456d9e743', false, false),
      ('e6h7i8j9-m901-2jkl-7o9p-i012j3k456l0', 'HERING', NULL, 'T_SHIRT', NULL, 'mostarda', CURRENT_TIMESTAMP, 'Camiseta vintage', 'algodão', 'UNISEX', 'Camiseta Mostarda', 13000, 'M', NULL, '8a3d0b3f-ce6c-4b18-b1d9-9d3456d9e743', false, false),
      ('g2i3j4k5-r456-7nop-l12s-n456o7p890q4', 'CEA', NULL, 'T_SHIRT', NULL, 'cinza mescla', CURRENT_TIMESTAMP, 'Camiseta ecológica', 'algodão', 'UNISEX', 'Camiseta Eco', 13000, 'L', NULL, 'af5f2d5f-ef8e-4d39-d3f1-1f5678e1f965', false, false),
      ('t1s2h3i4-r567-8def-g12h-i345j6k789l0', 'NIKE', NULL, 'T_SHIRT', NULL, 'azul', CURRENT_TIMESTAMP, 'Camiseta masculina', 'algodão', 'MALE', 'Camiseta Azul Nike', 15000, 'M', NULL, '9b4e1c4f-de7d-4c29-c2e0-0e4567d0f854', false, false),
      ('t2h3i4j5-s678-9efg-h23i-j456k7l890m1', 'ADIDAS', NULL, 'T_SHIRT', NULL, 'verde', CURRENT_TIMESTAMP, 'Camiseta esportiva', 'poliéster', 'MALE', 'Camiseta Verde Adidas', 16000, 'L', NULL, 'c3d4e5f6-g7h8-4i9j-k0l1-m2n3o4p5q6r7', false, false),
      ('t3i4j5k6-t789-0fgh-i34j-k567l8m901n2', 'ZARA', NULL, 'T_SHIRT', NULL, 'branco', CURRENT_TIMESTAMP, 'Camiseta feminina', 'algodão', 'FEMALE', 'Camiseta Branca Zara', 17000, 'S', NULL, 'a1b2c3d4-e5f6-4g7h-i8j9-k0l1m2n3o4p5', false, false),
      ('t4j5k6l7-u890-1ghi-j45k-l678m9n012o3', 'HERING', NULL, 'T_SHIRT', NULL, 'amarelo', CURRENT_TIMESTAMP, 'Camiseta casual', 'algodão', 'FEMALE', 'Camiseta Amarela', 14000, 'M', NULL, 'b2c3d4e5-f6g7-4h8i-j9k0-l1m2n3o4p5q6', false, false),
      ('t5k6l7m8-v901-2hij-k56l-m789n0o123p4', 'CEA', NULL, 'T_SHIRT', NULL, 'roxo', CURRENT_TIMESTAMP, 'Camiseta urbana', 'algodão', 'MALE', 'Camiseta Roxa CEA', 13000, 'L', NULL, 'd4e5f6g7-h8i9-4j0k-l1m2-n3o4p5q6r7s8', false, false),
      ('t6l7m8n9-w012-3ijk-l67m-n890o1p234q5', 'OTHER', 'Marca Artesanal', 'T_SHIRT', NULL, 'marrom', CURRENT_TIMESTAMP, 'Camiseta alternativa', 'algodão orgânico', 'UNISEX', 'Camiseta Marrom', 18000, 'M', NULL, 'e5f6g7h8-i9j0-4k1l-m2n3-o4p5q6r7s8t9', false, false),
      -- OTHER category clothes (2)
      ('o1t2h3e4-r567-8def-g12h-i345j6k789l0', 'OTHER', NULL, 'OTHER', NULL, 'dourado', CURRENT_TIMESTAMP, 'Acessório especial', 'metal', 'UNISEX', 'Peça Especial', 8000, 'OTHER', 'Único', '8ca95f23-217b-485e-bdfa-35fa862b925c', false, false),
      ('o2h3e4r5-s678-9efg-h23i-j456k7l890m1', 'OTHER', 'Joalheria Local', 'OTHER', 'Colar', 'prata', CURRENT_TIMESTAMP, 'Colar artesanal', 'prata', 'FEMALE', 'Colar Prata', 5000, 'OTHER', 'Único', '8ca95f23-217b-485e-bdfa-35fa862b925c', false, false),
      -- Additional clothes for Brecho da Edna (11 total for this store)
      ('c2b3d4e5-f234-5bcd-9f0g-b234c5d678e2', 'OTHER', 'Marca Genérica', 'DRESS', NULL, 'azul', CURRENT_TIMESTAMP, 'Vestido casual', 'seda', 'FEMALE', 'Vestido Azul', 18000, 'L', NULL, '8ca95f23-217b-485e-bdfa-35fa862b925c', false, false),
      ('c3d4e5f6-g345-6cde-af1h-c345d6e789f3', 'ZARA', NULL, 'PANTS', NULL, 'preto', CURRENT_TIMESTAMP, 'Calça formal', 'poliéster', 'UNISEX', 'Calça Zara', 16000, 'N_38', NULL, '8ca95f23-217b-485e-bdfa-35fa862b925c', false, false),
      ('e1f2g3h4-k789-0ghi-e45l-g789h0i123j7', 'FARM', NULL, 'DRESS', NULL, 'amarelo', CURRENT_TIMESTAMP, 'Vestido de verão', 'algodão', 'FEMALE', 'Vestido FARM', 19000, 'M', NULL, '8ca95f23-217b-485e-bdfa-35fa862b925c', false, false),
      ('f1g2h3i4-n012-3jkl-h78o-j012k3l456m0', 'NIKE', NULL, 'JACKET_HOODIE', NULL, 'azul marinho', CURRENT_TIMESTAMP, 'Moletom esportivo', 'algodão', 'MALE', 'Moletom Nike', 20000, 'L', NULL, '8ca95f23-217b-485e-bdfa-35fa862b925c', false, true),
      ('f2h3i4j5-o123-4klm-i89p-k123l4m567n1', 'RENNER', NULL, 'SHORTS', NULL, 'vermelho', CURRENT_TIMESTAMP, 'Short casual', 'jeans', 'UNISEX', 'Short Renner', 10000, 'M', NULL, '8ca95f23-217b-485e-bdfa-35fa862b925c', false, false),
      ('f3i4j5k6-p234-5lmn-j90q-l234m5n678o2', 'OTHER', 'Confecção Local', 'SUIT', 'Macacão', 'verde oliva', CURRENT_TIMESTAMP, 'Macacão estiloso', 'sarja', 'FEMALE', 'Macacão Verde', 15000, 'N_40', NULL, '8ca95f23-217b-485e-bdfa-35fa862b925c', false, true),
      ('g1h2i3j4-q345-6mno-k01r-m345n6o789p3', 'FARM', NULL, 'DRESS', NULL, 'verde claro', CURRENT_TIMESTAMP, 'Vestido ecológico', 'algodão orgânico', 'FEMALE', 'Vestido Eco', 17000, 'M', NULL, '8ca95f23-217b-485e-bdfa-35fa862b925c', false, false),
      ('g3j4k5l6-s567-8opq-m23t-o567p8q901r5', 'OTHER', 'Marca Sustentável', 'PANTS', NULL, 'marrom', CURRENT_TIMESTAMP, 'Calça eco-friendly', 'linho', 'MALE', 'Calça Sustentável', 14000, 'N_38', NULL, '8ca95f23-217b-485e-bdfa-35fa862b925c', false, true),
      ('l5m6n7o8-x012-3tuv-r78y-t012u3v456w0', 'ZARA', NULL, 'JACKET_HOODIE', NULL, 'cinza', CURRENT_TIMESTAMP, 'Jaqueta leve para meia-estação', 'nylon', 'UNISEX', 'Jaqueta Zara', 19500, 'L', NULL, '8ca95f23-217b-485e-bdfa-35fa862b925c', false, false),
      ('m6n7o8p9-y123-4uvw-s89z-u123v4w567x1', 'OTHER', 'Marca Independente', 'SHORTS', NULL, 'verde', CURRENT_TIMESTAMP, 'Short de verão confortável', 'algodão', 'MALE', 'Short Verde', 10500, 'M', NULL, '8ca95f23-217b-485e-bdfa-35fa862b925c', false, false),
      ('n7o8p9q0-z234-5vwx-t90a-v234w5x678y2', 'FARM', NULL, 'DRESS', NULL, 'laranja', CURRENT_TIMESTAMP, 'Vestido leve e colorido', 'linho', 'FEMALE', 'Vestido FARM Laranja', 18500, 'M', NULL, '8ca95f23-217b-485e-bdfa-35fa862b925c', false, false),
      ('o8p9q0r1-a345-6wxy-u01b-w345x6y789z3', 'RENNER', NULL, 'PANTS', NULL, 'azul escuro', CURRENT_TIMESTAMP, 'Calça social moderna', 'sintético', 'FEMALE', 'Calça Renner', 16500, 'N_38', NULL, '8ca95f23-217b-485e-bdfa-35fa862b925c', false, false),
      -- Additional clothes for other stores
      ('c5e6f7g8-f234-5efg-0h2i-b345c6d789e3', 'ZARA', NULL, 'PANTS', NULL, 'cinza', CURRENT_TIMESTAMP, 'Calça casual moderna', 'jeans', 'FEMALE', 'Calça Zara Feminina', 17000, 'N_40', NULL, '6e1f8c1e-d229-4e98-99c2-7a6239a8b531', false, false),
      ('c6f7g8h9-g345-6fgh-1i3j-c456d7e890f4', 'OTHER', 'Marca Experimental', 'JACKET_HOODIE', NULL, 'bege', CURRENT_TIMESTAMP, 'Jaqueta leve', 'nylon', 'UNISEX', 'Jaqueta Experimental', 19000, 'M', NULL, '6e1f8c1e-d229-4e98-99c2-7a6239a8b531', false, false );

INSERT INTO public.clothe_image (id, created_at, deleted, url, clothe_id) VALUES
-- T_SHIRT clothes images
('img-01', CURRENT_TIMESTAMP, false, 'male-tshirt-1.png', 'c1a2b3c4-d123-4abc-8e9f-a123b4c567d1'),
('img-02', CURRENT_TIMESTAMP, false, 'male-tshirt-2.png', 'c4d5e6f7-e123-4def-9g1h-a234b5c678d2'),
('img-03', CURRENT_TIMESTAMP, false, 'male-tshirt-3.png', 'd6g7h8i9-j678-9ijk-4l6m-f789g0h123i7'),
('img-04', CURRENT_TIMESTAMP, false, 'female-tshirt-1.png', 'e2g3h4i5-l890-1hij-f56m-h890i1j234k8'),
('img-05', CURRENT_TIMESTAMP, false, 'female-tshirt-2.png', 'e6h7i8j9-m901-2jkl-7o9p-i012j3k456l0'),
('img-06', CURRENT_TIMESTAMP, false, 'male-tshirt-4.png', 'g2i3j4k5-r456-7nop-l12s-n456o7p890q4'),
('img-07', CURRENT_TIMESTAMP, false, 'male-tshirt-5.png', 't1s2h3i4-r567-8def-g12h-i345j6k789l0'),
('img-08', CURRENT_TIMESTAMP, false, 'male-tshirt-1.png', 't2h3i4j5-s678-9efg-h23i-j456k7l890m1'),
('img-09', CURRENT_TIMESTAMP, false, 'female-tshirt-3.png', 't3i4j5k6-t789-0fgh-i34j-k567l8m901n2'),
('img-10', CURRENT_TIMESTAMP, false, 'female-tshirt-4.png', 't4j5k6l7-u890-1ghi-j45k-l678m9n012o3'),
('img-11', CURRENT_TIMESTAMP, false, 'male-tshirt-2.png', 't5k6l7m8-v901-2hij-k56l-m789n0o123p4'),
('img-12', CURRENT_TIMESTAMP, false, 'female-tshirt-5.png', 't6l7m8n9-w012-3ijk-l67m-n890o1p234q5'),
-- OTHER category images
('img-13', CURRENT_TIMESTAMP, false, 'male-tshirt-3.png', 'o1t2h3e4-r567-8def-g12h-i345j6k789l0'),
('img-14', CURRENT_TIMESTAMP, false, 'female-tshirt-1.png', 'o2h3e4r5-s678-9efg-h23i-j456k7l890m1'),
-- Brecho da Edna additional clothes
('img-15', CURRENT_TIMESTAMP, false, 'female-dress-1.png', 'c2b3d4e5-f234-5bcd-9f0g-b234c5d678e2'),
('img-16', CURRENT_TIMESTAMP, false, 'male-pants-1.png', 'c3d4e5f6-g345-6cde-af1h-c345d6e789f3'),
('img-17', CURRENT_TIMESTAMP, false, 'female-dress-2.png', 'e1f2g3h4-k789-0ghi-e45l-g789h0i123j7'),
('img-18', CURRENT_TIMESTAMP, false, 'male-hoodie-1.png', 'f1g2h3i4-n012-3jkl-h78o-j012k3l456m0'),
('img-19', CURRENT_TIMESTAMP, false, 'male-pants-2.png', 'f2h3i4j5-o123-4klm-i89p-k123l4m567n1'),
('img-20', CURRENT_TIMESTAMP, false, 'female-dress-1.png', 'f3i4j5k6-p234-5lmn-j90q-l234m5n678o2'),
('img-21', CURRENT_TIMESTAMP, false, 'female-dress-2.png', 'g1h2i3j4-q345-6mno-k01r-m345n6o789p3'),
('img-22', CURRENT_TIMESTAMP, false, 'male-pants-1.png', 'g3j4k5l6-s567-8opq-m23t-o567p8q901r5'),
('img-25', CURRENT_TIMESTAMP, false, 'male-hoodie-1.png', 'l5m6n7o8-x012-3tuv-r78y-t012u3v456w0'),
('img-26', CURRENT_TIMESTAMP, false, 'male-pants-1.png', 'm6n7o8p9-y123-4uvw-s89z-u123v4w567x1'),
('img-27', CURRENT_TIMESTAMP, false, 'female-dress-1.png', 'n7o8p9q0-z234-5vwx-t90a-v234w5x678y2'),
('img-28', CURRENT_TIMESTAMP, false, 'female-pants-2.png', 'o8p9q0r1-a345-6wxy-u01b-w345x6y789z3'),
-- Other stores clothes
('img-23', CURRENT_TIMESTAMP, false, 'female-pants-1.png', 'c5e6f7g8-f234-5efg-0h2i-b345c6d789e3'),
('img-24', CURRENT_TIMESTAMP, false, 'female-hoodie-1.png', 'c6f7g8h9-g345-6fgh-1i3j-c456d7e890f4');


INSERT INTO public.address (id, cep, city, created_at, neighborhood, "number", street, store_id)
VALUES
    ('5db49ab1-f98a-44f1-bf5c-e6d92d6974e0', '88010000', 'Florianópolis', NOW(), 'Centro', 350, 'Rua Felipe Schmidt', '6e1f8c1e-d229-4e98-99c2-7a6239a8b531'),
    ('ba94d9de-93a2-4f75-b07d-82b0e59d1975', '88025000', 'Florianópolis', NOW(), 'Trindade', 561, 'Rua Lauro Linhares', '7f2c9a2e-bd5b-4a09-a3b8-8c2347c8d632'),
    ('e8507b9f-b177-453f-91db-85039473b32f', '88037001', 'Florianópolis', NOW(), 'Itacorubi', 2024, 'Rodovia Admar Gonzaga', '8a3d0b3f-ce6c-4b18-b1d9-9d3456d9e743'),
    ('a32c9e4f-6f3b-4a2e-88b1-1a5d6b0f342e', '88062000', 'Florianópolis', NOW(), 'Lagoa da Conceição', 150, 'Avenida das Rendeiras', '9b4e1c4f-de7d-4c29-c2e0-0e4567d0f854'),
    ('f7c38fbe-cf65-42fc-a0fc-e9b0e0b4db7d', '88062000', 'Florianópolis', NOW(), 'Lagoa da Conceição', 3790, 'Avenida das Rendeiras', 'af5f2d5f-ef8e-4d39-d3f1-1f5678e1f965'),
    ('c7533463-a85e-4f91-a702-270905e61c9a', '88064001', 'Florianópolis', NOW(), 'Ribeirão da Ilha', 2855, 'Rodovia Baldicero Filomeno', '8ca95f23-217b-485e-bdfa-35fa862b925c'),
    ('a1b2c3d4-e5f6-7g8h-9i0j-k1l2m3n4o5p6', '88020000', 'Florianópolis', NOW(), 'Estreito', 1250, 'Rua Santos Saraiva', 'a1b2c3d4-e5f6-4g7h-i8j9-k0l1m2n3o4p5'),
    ('b2c3d4e5-f6g7-8h9i-0j1k-l2m3n4o5p6q7', '88040000', 'Florianópolis', NOW(), 'Córrego Grande', 890, 'Rua Delfino Conti', 'b2c3d4e5-f6g7-4h8i-j9k0-l1m2n3o4p5q6'),
    ('c3d4e5f6-g7h8-9i0j-1k2l-m3n4o5p6q7r8', '88030000', 'Florianópolis', NOW(), 'Agronômica', 445, 'Rua Esteves Júnior', 'c3d4e5f6-g7h8-4i9j-k0l1-m2n3o4p5q6r7'),
    ('d4e5f6g7-h8i9-0j1k-2l3m-n4o5p6q7r8s9', '88050000', 'Florianópolis', NOW(), 'Saco Grande', 1678, 'Rua João Pio Duarte Silva', 'd4e5f6g7-h8i9-4j0k-l1m2-n3o4p5q6r7s8'),
    ('e5f6g7h8-i9j0-1k2l-3m4n-o5p6q7r8s9t0', '88015000', 'Florianópolis', NOW(), 'Pantanal', 823, 'Rua Bocaiuva', 'e5f6g7h8-i9j0-4k1l-m2n3-o4p5q6r7s8t9');



INSERT INTO public.store_day_schedule (
    id, closing_time_in_minutes, day_of_week, opening_time_in_minutes, store_id, enabled
)
VALUES
    ('c931a058-0b70-467f-b080-5967599d3a40', 1080, 0, 480, '6e1f8c1e-d229-4e98-99c2-7a6239a8b531', false),
    ('c531ea87-9b1b-48b1-b32e-741d301ed775', 1080, 1, 480, '6e1f8c1e-d229-4e98-99c2-7a6239a8b531', true),
    ('ef50e7cf-d6b2-44ea-8d4f-6012021c3cd3', 1080, 2, 480, '6e1f8c1e-d229-4e98-99c2-7a6239a8b531', true),
    ('6b053c75-2b1d-4e97-82db-e5f94d7d0f85', 1080, 3, 480, '6e1f8c1e-d229-4e98-99c2-7a6239a8b531', true),
    ('d041d1d0-443f-4044-b1d7-fd9f6bc51f1c', 1080, 4, 480, '6e1f8c1e-d229-4e98-99c2-7a6239a8b531', true),
    ('f4a321c7-2e0d-475f-b319-4e62036d906d', 1080, 5, 480, '6e1f8c1e-d229-4e98-99c2-7a6239a8b531', true),
    ('beb93f89-f9c2-4607-9540-9adc1227be6a', 1080, 6, 480, '6e1f8c1e-d229-4e98-99c2-7a6239a8b531', false),
    ('bb70b2b2-e1ad-4871-b7e0-3a1ca28b8b66', 1080, 1, 480, '7f2c9a2e-bd5b-4a09-a3b8-8c2347c8d632', true),
    ('5ad798a3-b59c-4b88-81b7-f7b65a2b50d1', 1080, 2, 480, '7f2c9a2e-bd5b-4a09-a3b8-8c2347c8d632', true),
    ('a2496cfe-bf65-4f67-a0cc-c2d8a43d3d1b', 1080, 3, 480, '7f2c9a2e-bd5b-4a09-a3b8-8c2347c8d632', true),
    ('430c2e8b-9b12-4531-8a02-bdfe32e054e4', 1080, 4, 480, '7f2c9a2e-bd5b-4a09-a3b8-8c2347c8d632', true),
    ('d308fa9c-9532-46ad-89ea-c4c41e6d604f', 1080, 5, 480, '7f2c9a2e-bd5b-4a09-a3b8-8c2347c8d632', true),
    ('2ef9e80a-0a9c-46b1-b410-5e4b1f36d3ac', 1080, 1, 480, '8a3d0b3f-ce6c-4b18-b1d9-9d3456d9e743', true),
    ('9a52d5f3-e4e7-44e6-8c62-d5d3d31e77ed', 1080, 2, 480, '8a3d0b3f-ce6c-4b18-b1d9-9d3456d9e743', true),
    ('c8c1b9a3-8880-4c29-bfba-9fc4606c9b3c', 1080, 3, 480, '8a3d0b3f-ce6c-4b18-b1d9-9d3456d9e743', true),
    ('23e3e90f-b6ad-4b8d-80d6-5f0f1c5d68e3', 1080, 4, 480, '8a3d0b3f-ce6c-4b18-b1d9-9d3456d9e743', true),
    ('9d230924-c8f6-4c2d-a724-e0a5cf5f8f9f', 1080, 5, 480, '8a3d0b3f-ce6c-4b18-b1d9-9d3456d9e743', true),
    ('f575d1c0-7d83-4562-9fef-cedb88d0da4e', 1080, 1, 480, '9b4e1c4f-de7d-4c29-c2e0-0e4567d0f854', true),
    ('97d10b4f-6a2e-402f-9b45-8c43f8c3b86d', 1080, 2, 480, '9b4e1c4f-de7d-4c29-c2e0-0e4567d0f854', true),
    ('6ed6fc52-ff83-4db2-b9ae-2b5b40d90969', 1080, 3, 480, '9b4e1c4f-de7d-4c29-c2e0-0e4567d0f854', true),
    ('8e5ca7e1-cc2c-44cc-951f-80f462e9ae1d', 1080, 4, 480, '9b4e1c4f-de7d-4c29-c2e0-0e4567d0f854', true),
    ('0e0de23e-83a0-4758-b877-036e509a2999', 1080, 5, 480, '9b4e1c4f-de7d-4c29-c2e0-0e4567d0f854', true),
    ('8c0ed67d-6fe5-43db-bbf1-0837c27d9e5f', 1080, 1, 480, 'af5f2d5f-ef8e-4d39-d3f1-1f5678e1f965', true),
    ('f329ee17-c88c-4133-98c7-d3b4bdb3b8a7', 1080, 2, 480, 'af5f2d5f-ef8e-4d39-d3f1-1f5678e1f965', true),
    ('73680e2d-0847-4d87-b1fd-c013a545060f', 1080, 3, 480, 'af5f2d5f-ef8e-4d39-d3f1-1f5678e1f965', true),
    ('622d6603-5c7d-4bb4-85d6-5fa67e2e61d8', 1080, 4, 480, 'af5f2d5f-ef8e-4d39-d3f1-1f5678e1f965', true),
    ('9a8f67e1-f7be-4d72-89bb-e91e04989d9c', 1080, 5, 480, 'af5f2d5f-ef8e-4d39-d3f1-1f5678e1f965', true),
    ('f7873961-c2f0-4dde-95b3-5827cec31c70', 1080, 0, 480, '8ca95f23-217b-485e-bdfa-35fa862b925c', false),
    ('c5j1ea87-9b1b-48b1-b32e-741d301ed775', 1080, 1, 480, '8ca95f23-217b-485e-bdfa-35fa862b925c', true),
    ('efn0e7cf-d6b2-44ea-8d4f-6012021c3cd3', 1080, 2, 480, '8ca95f23-217b-485e-bdfa-35fa862b925c', true),
    ('6bk53c75-2b1d-4e97-82db-e5f94d7d0f85', 1080, 3, 480, '8ca95f23-217b-485e-bdfa-35fa862b925c', true),
    ('d0p1d1d0-443f-4044-b1d7-fd9f6bc51f1c', 1080, 4, 480, '8ca95f23-217b-485e-bdfa-35fa862b925c', true),
    ('f4l321c7-2e0d-475f-b319-4e62036d906d', 1080, 5, 480, '8ca95f23-217b-485e-bdfa-35fa862b925c', true),
    ('bei93f89-f9c2-4607-9540-9adc1227be6a', 1080, 6, 480, '8ca95f23-217b-485e-bdfa-35fa862b925c', false),
    ('da1c2056-30f1-4209-8094-2aa946c5a29a', 1080, 1, 480, 'a1b2c3d4-e5f6-4g7h-i8j9-k0l1m2n3o4p5', true),
    ('57f7d72b-22a2-4cf9-859c-62529924bc77', 1080, 2, 480, 'a1b2c3d4-e5f6-4g7h-i8j9-k0l1m2n3o4p5', true),
    ('9f293d45-44d3-4c45-b028-9391a63e31f7', 1080, 3, 480, 'a1b2c3d4-e5f6-4g7h-i8j9-k0l1m2n3o4p5', true),
    ('e87c29f9-19ef-4268-8c80-f5ffb97cbd2a', 1080, 4, 480, 'a1b2c3d4-e5f6-4g7h-i8j9-k0l1m2n3o4p5', true),
    ('bc6fc3b6-c579-49f4-8b36-64cb7861be06', 1080, 5, 480, 'a1b2c3d4-e5f6-4g7h-i8j9-k0l1m2n3o4p5', true),
    ('b2e40555-d0b2-4e1f-a738-e4fe11c4dff4', 1080, 1, 480, 'b2c3d4e5-f6g7-4h8i-j9k0-l1m2n3o4p5q6', true),
    ('901f9e80-3c32-42b2-9eae-bf173dd3996f', 1080, 2, 480, 'b2c3d4e5-f6g7-4h8i-j9k0-l1m2n3o4p5q6', true),
    ('a3131e13-3cf9-42ff-a297-1ee7d48a7f9d', 1080, 3, 480, 'b2c3d4e5-f6g7-4h8i-j9k0-l1m2n3o4p5q6', true),
    ('76401660-7db9-4a8b-a0a2-4e1ccff46d80', 1080, 4, 480, 'b2c3d4e5-f6g7-4h8i-j9k0-l1m2n3o4p5q6', true),
    ('1a1d2581-e394-40ec-9a3f-2f156dbfa4eb', 1080, 5, 480, 'b2c3d4e5-f6g7-4h8i-j9k0-l1m2n3o4p5q6', true),
    ('7b16146f-7ef8-49cf-acc2-c296e14cbd82', 1080, 1, 480, 'c3d4e5f6-g7h8-4i9j-k0l1-m2n3o4p5q6r7', true),
    ('8827333f-054e-4f3c-9e5a-3291f17829a9', 1080, 2, 480, 'c3d4e5f6-g7h8-4i9j-k0l1-m2n3o4p5q6r7', true),
    ('d8d5a304-f2f2-426c-a3eb-bfcd2ff118c5', 1080, 3, 480, 'c3d4e5f6-g7h8-4i9j-k0l1-m2n3o4p5q6r7', true),
    ('43ef8f8b-dc48-4a60-95a2-3f7fd108b819', 1080, 4, 480, 'c3d4e5f6-g7h8-4i9j-k0l1-m2n3o4p5q6r7', true),
    ('70abec16-5702-4291-9d35-05092d9e6d0d', 1080, 5, 480, 'c3d4e5f6-g7h8-4i9j-k0l1-m2n3o4p5q6r7', true),
    ('fdce9942-c9d6-4553-b057-6d6e3d5e2d3e', 1080, 1, 480, 'd4e5f6g7-h8i9-4j0k-l1m2-n3o4p5q6r7s8', true),
    ('a499e00c-5e29-401c-a683-e3bc4a1f65ef', 1080, 2, 480, 'd4e5f6g7-h8i9-4j0k-l1m2-n3o4p5q6r7s8', true),
    ('865c28cc-68c1-4a98-a48f-f91dcbb8c7a6', 1080, 3, 480, 'd4e5f6g7-h8i9-4j0k-l1m2-n3o4p5q6r7s8', true),
    ('51d42e83-7cc3-423c-8577-2b8fcb62b6d6', 1080, 4, 480, 'd4e5f6g7-h8i9-4j0k-l1m2-n3o4p5q6r7s8', true),
    ('3e444f2b-702c-4cb6-b01d-7073f6b8f973', 1080, 5, 480, 'd4e5f6g7-h8i9-4j0k-l1m2-n3o4p5q6r7s8', true),
    ('2f8de6bc-c1b1-4adf-b607-f7c79cc7ad6f', 1080, 1, 480, 'e5f6g7h8-i9j0-4k1l-m2n3-o4p5q6r7s8t9', true),
    ('67cc8f18-bf94-4c37-bc37-17df8a838b55', 1080, 2, 480, 'e5f6g7h8-i9j0-4k1l-m2n3-o4p5q6r7s8t9', true),
    ('b65cfdc0-e25a-497f-9764-0f8f0dd8a229', 1080, 3, 480, 'e5f6g7h8-i9j0-4k1l-m2n3-o4p5q6r7s8t9', true),
    ('7f878fe3-fc8f-4d2b-8904-22944f24ac43', 1080, 4, 480, 'e5f6g7h8-i9j0-4k1l-m2n3-o4p5q6r7s8t9', true),
    ('2a138c4b-6220-4dd3-872e-9e7385b1fdc2', 1080, 5, 480, 'e5f6g7h8-i9j0-4k1l-m2n3-o4p5q6r7s8t9', true);




INSERT INTO public.clothe_order (id, created_at, status, clothe_id, customer_id, store_id, is_first_order, rating) VALUES
-- Orders for Brechó Bella (6e1f8c1e-d229-4e98-99c2-7a6239a8b531)
('order-bb1', CURRENT_TIMESTAMP, 'COMPLETED', 'c4d5e6f7-e123-4def-9g1h-a234b5c678d2', 'd290f1ee-6c54-4b01-90e6-d701748f0851', '6e1f8c1e-d229-4e98-99c2-7a6239a8b531', false, 5),
('order-bb2', CURRENT_TIMESTAMP, 'COMPLETED', 'c5e6f7g8-f234-5efg-0h2i-b345c6d789e3', '2a6fa6dd-8b1f-4d3d-97cb-20adcf11cc74', '6e1f8c1e-d229-4e98-99c2-7a6239a8b531', false, 4),
('order-bb3', CURRENT_TIMESTAMP, 'COMPLETED', 'c6f7g8h9-g345-6fgh-1i3j-c456d7e890f4', '3b9f0b7c-a7e2-47c5-bc88-9f2522d6e78e', '6e1f8c1e-d229-4e98-99c2-7a6239a8b531', false, 3),
-- Orders for Vintage Style (7f2c9a2e-bd5b-4a09-a3b8-8c2347c8d632)
('order-vs1', CURRENT_TIMESTAMP, 'COMPLETED', 'd6g7h8i9-j678-9ijk-4l6m-f789g0h123i7', '4c8e1d08-5ae7-4b66-8c6b-563d44f872ed', '7f2c9a2e-bd5b-4a09-a3b8-8c2347c8d632', false, 5),
('order-vs2', CURRENT_TIMESTAMP, 'COMPLETED', 'g2i3j4k5-r456-7nop-l12s-n456o7p890q4', '5d7d9e6f-8c4d-4c32-b0d5-372f9eb7fd43', '7f2c9a2e-bd5b-4a09-a3b8-8c2347c8d632', false, 2),
('order-vs3', CURRENT_TIMESTAMP, 'COMPLETED', 'e6h7i8j9-m901-2jkl-7o9p-i012j3k456l0', '6e8f7g9h-9d5e-4d43-c1f6-483f0fc8ge54', '7f2c9a2e-bd5b-4a09-a3b8-8c2347c8d632', false, 3),
-- Orders for Chic Retrô (8a3d0b3f-ce6c-4b18-b1d9-9d3456d9e743)
('order-cr1', CURRENT_TIMESTAMP, 'COMPLETED', 'e2g3h4i5-l890-1hij-f56m-h890i1j234k8', '7f9g8h0i-0e6f-4e54-d2g7-594g1gd9hf65', '8a3d0b3f-ce6c-4b18-b1d9-9d3456d9e743', false, 4),
('order-cr2', CURRENT_TIMESTAMP, 'COMPLETED', 't3i4j5k6-t789-0fgh-i34j-k567l8m901n2', '8g0h9i1j-1f7g-4f65-e3h8-605h2he0ig76', '8a3d0b3f-ce6c-4b18-b1d9-9d3456d9e743', false, 2),
('order-cr3', CURRENT_TIMESTAMP, 'COMPLETED', 't4j5k6l7-u890-1ghi-j45k-l678m9n012o3', 'd290f1ee-6c54-4b01-90e6-d701748f0851', '8a3d0b3f-ce6c-4b18-b1d9-9d3456d9e743', false, 1),
-- Orders for Brecho da Edna (8ca95f23-217b-485e-bdfa-35fa862b925c) - required 3 orders
('order-be1', CURRENT_TIMESTAMP, 'CANCELED', 'f1g2h3i4-n012-3jkl-h78o-j012k3l456m0', '2a6fa6dd-8b1f-4d3d-97cb-20adcf11cc74', '8ca95f23-217b-485e-bdfa-35fa862b925c', false, NULL),
('order-be2', CURRENT_TIMESTAMP, 'COMPLETED', 'f3i4j5k6-p234-5lmn-j90q-l234m5n678o2', '3b9f0b7c-a7e2-47c5-bc88-9f2522d6e78e', '8ca95f23-217b-485e-bdfa-35fa862b925c', false, 5),
('order-be3', CURRENT_TIMESTAMP, 'AWAITING_WITHDRAWAL', 'g3j4k5l6-s567-8opq-m23t-o567p8q901r5', '4c8e1d08-5ae7-4b66-8c6b-563d44f872ed', '8ca95f23-217b-485e-bdfa-35fa862b925c', false, NULL);


INSERT INTO public.saved_clothe (id, created_at, clothe_id, customer_id) VALUES
-- Clothe with 8 saves (using 't1s2h3i4-r567-8def-g12h-i345j6k789l0' - Camiseta Azul Nike)
('save-01', CURRENT_TIMESTAMP, 't1s2h3i4-r567-8def-g12h-i345j6k789l0', 'd290f1ee-6c54-4b01-90e6-d701748f0851'),
('save-02', CURRENT_TIMESTAMP, 't1s2h3i4-r567-8def-g12h-i345j6k789l0', '2a6fa6dd-8b1f-4d3d-97cb-20adcf11cc74'),
('save-03', CURRENT_TIMESTAMP, 't1s2h3i4-r567-8def-g12h-i345j6k789l0', '3b9f0b7c-a7e2-47c5-bc88-9f2522d6e78e'),
('save-04', CURRENT_TIMESTAMP, 't1s2h3i4-r567-8def-g12h-i345j6k789l0', '4c8e1d08-5ae7-4b66-8c6b-563d44f872ed'),
('save-05', CURRENT_TIMESTAMP, 't1s2h3i4-r567-8def-g12h-i345j6k789l0', '5d7d9e6f-8c4d-4c32-b0d5-372f9eb7fd43'),
('save-06', CURRENT_TIMESTAMP, 't1s2h3i4-r567-8def-g12h-i345j6k789l0', '6e8f7g9h-9d5e-4d43-c1f6-483f0fc8ge54'),
('save-07', CURRENT_TIMESTAMP, 't1s2h3i4-r567-8def-g12h-i345j6k789l0', '7f9g8h0i-0e6f-4e54-d2g7-594g1gd9hf65'),
('save-08', CURRENT_TIMESTAMP, 't1s2h3i4-r567-8def-g12h-i345j6k789l0', '8g0h9i1j-1f7g-4f65-e3h8-605h2he0ig76'),
-- Clothe with 5 saves (using 'e1f2g3h4-k789-0ghi-e45l-g789h0i123j7' - Vestido FARM)
('save-09', CURRENT_TIMESTAMP, 'e1f2g3h4-k789-0ghi-e45l-g789h0i123j7', 'd290f1ee-6c54-4b01-90e6-d701748f0851'),
('save-10', CURRENT_TIMESTAMP, 'e1f2g3h4-k789-0ghi-e45l-g789h0i123j7', '2a6fa6dd-8b1f-4d3d-97cb-20adcf11cc74'),
('save-11', CURRENT_TIMESTAMP, 'e1f2g3h4-k789-0ghi-e45l-g789h0i123j7', '3b9f0b7c-a7e2-47c5-bc88-9f2522d6e78e'),
('save-12', CURRENT_TIMESTAMP, 'e1f2g3h4-k789-0ghi-e45l-g789h0i123j7', '4c8e1d08-5ae7-4b66-8c6b-563d44f872ed'),
('save-13', CURRENT_TIMESTAMP, 'e1f2g3h4-k789-0ghi-e45l-g789h0i123j7', '5d7d9e6f-8c4d-4c32-b0d5-372f9eb7fd43'),
-- Clothe with 3 saves (using 'c2b3d4e5-f234-5bcd-9f0g-b234c5d678e2' - Vestido Azul)
('save-14', CURRENT_TIMESTAMP, 'c2b3d4e5-f234-5bcd-9f0g-b234c5d678e2', '6e8f7g9h-9d5e-4d43-c1f6-483f0fc8ge54'),
('save-15', CURRENT_TIMESTAMP, 'c2b3d4e5-f234-5bcd-9f0g-b234c5d678e2', '7f9g8h0i-0e6f-4e54-d2g7-594g1gd9hf65'),
('save-16', CURRENT_TIMESTAMP, 'c2b3d4e5-f234-5bcd-9f0g-b234c5d678e2', '8g0h9i1j-1f7g-4f65-e3h8-605h2he0ig76');
