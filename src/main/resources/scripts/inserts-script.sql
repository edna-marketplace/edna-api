INSERT INTO edna.customer (
    id, created_at, email, "password", phone, cpf, name, deleted
) VALUES
      ('d290f1ee-6c54-4b01-90e6-d701748f0851', CURRENT_TIMESTAMP, 'joao.silva@example.com', 'password123', '11987654321', '48750168088', 'João Silva', false),
      ('2a6fa6dd-8b1f-4d3d-97cb-20adcf11cc74', CURRENT_TIMESTAMP, 'maria.oliveira@example.com', 'password456', '11987654322', '82416611003', 'Maria Oliveira', false),
      ('3b9f0b7c-a7e2-47c5-bc88-9f2522d6e78e', CURRENT_TIMESTAMP, 'pedro.souza@example.com', 'password789', '11987654323', '87024830093', 'Pedro Souza', false),
      ('4c8e1d08-5ae7-4b66-8c6b-563d44f872ed', CURRENT_TIMESTAMP, 'ana.costa@example.com', 'password101', '11987654324', '94448025071', 'Ana Costa', false),
      ('5d7d9e6f-8c4d-4c32-b0d5-372f9eb7fd43', CURRENT_TIMESTAMP, 'carla.pereira@example.com', 'password202', '11987654325', '09394763040', 'Carla Pereira', false);

INSERT INTO edna.store (
    id, created_at, email, "password", phone, cnpj, name, target_customer, deleted
) VALUES
      ('6e1f8c1e-d229-4e98-99c2-7a6239a8b531', CURRENT_TIMESTAMP, 'brecho.bella@example.com', 'securepass1', '11976543210', '87736276000110', 'Brechó Bella', 'FEMALE', false),
      ('7f2c9a2e-bd5b-4a09-a3b8-8c2347c8d632', CURRENT_TIMESTAMP, 'vintage.style@example.com', 'securepass2', '11976543211', '57768821000190', 'Vintage Style', 'ALL', false),
      ('8a3d0b3f-ce6c-4b18-b1d9-9d3456d9e743', CURRENT_TIMESTAMP, 'chic.retro@example.com', 'securepass3', '11976543212', '84577905000119', 'Chic Retrô', 'FEMALE', false),
      ('9b4e1c4f-de7d-4c29-c2e0-0e4567d0f854', CURRENT_TIMESTAMP, 'urban.wear@example.com', 'securepass4', '11976543213', '57556282000125', 'Urban Wear', 'MALE', false),
      ('af5f2d5f-ef8e-4d39-d3f1-1f5678e1f965', CURRENT_TIMESTAMP, 'eco.brecho@example.com', 'securepass5', '11976543214', '12872344000170', 'Eco Brechó', 'ALL', false);

INSERT INTO edna.store_image (id, created_at, deleted, url, type, store_id) VALUES
-- Multiple images
('s-img-01', CURRENT_TIMESTAMP, false, 'mock-female-store.png', 'PROFILE', '6e1f8c1e-d229-4e98-99c2-7a6239a8b531'),
('s-img-02', CURRENT_TIMESTAMP, false, 'mock-male-store.png', 'PROFILE', '9b4e1c4f-de7d-4c29-c2e0-0e4567d0f854'),
('s-img-03', CURRENT_TIMESTAMP, false, 'mock-all-store.png', 'PROFILE', '7f2c9a2e-bd5b-4a09-a3b8-8c2347c8d632');

INSERT INTO edna.clothe (
    id, brand, brand_other, category, category_other, color, created_at, description, fabric, gender, "name", price_in_cents, "size", size_other, store_id, deleted, ordered
) VALUES
      -- Brechó Bella clothes
      ('c1a2b3c4-d123-4abc-8e9f-a123b4c567d1', 'NIKE', NULL, 'T_SHIRT', NULL, 'vermelho', CURRENT_TIMESTAMP, 'Camiseta esportiva', 'algodão', 'MALE', 'Camiseta Nike', 12000, 'M', NULL, '6e1f8c1e-d229-4e98-99c2-7a6239a8b531', false, false),
      ('c2b3d4e5-f234-5bcd-9f0g-b234c5d678e2', 'OTHER', 'Marca Genérica', 'DRESS', NULL, 'azul', CURRENT_TIMESTAMP, 'Vestido casual', 'seda', 'FEMALE', 'Vestido Azul', 25000, 'L', NULL, '6e1f8c1e-d229-4e98-99c2-7a6239a8b531', false, false),
      ('c3d4e5f6-g345-6cde-af1h-c345d6e789f3', 'ZARA', NULL, 'PANTS', NULL, 'preto', CURRENT_TIMESTAMP, 'Calça formal', 'poliéster', 'UNISEX',  'Calça Zara', 18000, 'N_38', NULL, '6e1f8c1e-d229-4e98-99c2-7a6239a8b531', false, false),
      ('c4d5e6f7-e123-4def-9g1h-a234b5c678d2', 'NIKE', NULL, 'T_SHIRT', NULL, 'preto', CURRENT_TIMESTAMP, 'Camiseta casual preta', 'algodão', 'MALE', 'Camiseta Preta Nike', 11000, 'L', NULL, '6e1f8c1e-d229-4e98-99c2-7a6239a8b531', false, true),
      ('c5e6f7g8-f234-5efg-0h2i-b345c6d789e3', 'ZARA', NULL, 'PANTS', NULL, 'cinza', CURRENT_TIMESTAMP, 'Calça casual moderna', 'jeans', 'FEMALE', 'Calça Zara Feminina', 17000, 'N_40', NULL, '6e1f8c1e-d229-4e98-99c2-7a6239a8b531', false, true),
      ('c6f7g8h9-g345-6fgh-1i3j-c456d7e890f4', 'OTHER', 'Marca Experimental', 'JACKET_HOODIE', NULL, 'bege', CURRENT_TIMESTAMP, 'Jaqueta leve', 'nylon', 'UNISEX', 'Jaqueta Experimental', 20000, 'M', NULL, '6e1f8c1e-d229-4e98-99c2-7a6239a8b531', false, true),
      -- Vintage Style clothes
      ('d1e2f3g4-h456-7def-b12i-d456e7f890g4', 'ADIDAS', NULL, 'SHORTS', NULL, 'verde', CURRENT_TIMESTAMP, 'Short esportivo', 'algodão', 'MALE', 'Short Adidas', 8000, 'M', NULL, '7f2c9a2e-bd5b-4a09-a3b8-8c2347c8d632', false, false),
      ('d2f3g4h5-i567-8efg-c23j-e567f8g901h5', 'OTHER', 'Marca Alternativa', 'JACKET_HOODIE', NULL, 'branco', CURRENT_TIMESTAMP, 'Macacão casual', 'lã', 'FEMALE', 'Macacão Branco', 20000, 'L', NULL, '7f2c9a2e-bd5b-4a09-a3b8-8c2347c8d632', false, false),
      ('d3g4h5i6-j678-9fgh-d34k-f678g9h012i6', 'CEA', NULL, 'JACKET_HOODIE', NULL, 'cinza', CURRENT_TIMESTAMP, 'Moletom casual', 'algodão', 'UNISEX', 'Moletom CEA', 15000, 'XL_LARGER', NULL, '7f2c9a2e-bd5b-4a09-a3b8-8c2347c8d632', false, false),
      ('d4e5f6g7-h456-7ghi-2j4k-d567e8f901g5', 'ADIDAS', NULL, 'SHORTS', NULL, 'preto', CURRENT_TIMESTAMP, 'Short retrô', 'poliéster', 'MALE', 'Short Retrô Adidas', 9000, 'L', NULL, '7f2c9a2e-bd5b-4a09-a3b8-8c2347c8d632', false, true),
      ('d5f6g7h8-i567-8hij-3k5l-e678f9g012h6', 'OTHER', 'Edição Limitada', 'DRESS', NULL, 'roxo', CURRENT_TIMESTAMP, 'Vestido vintage', 'seda', 'FEMALE', 'Vestido Roxo Vintage', 26000, 'M', NULL, '7f2c9a2e-bd5b-4a09-a3b8-8c2347c8d632', false, true),
      ('d6g7h8i9-j678-9ijk-4l6m-f789g0h123i7', 'CEA', NULL, 'T_SHIRT', NULL, 'laranja', CURRENT_TIMESTAMP, 'Camiseta retrô', 'algodão', 'UNISEX', 'Camiseta Laranja CEA', 12000, 'S', NULL, '7f2c9a2e-bd5b-4a09-a3b8-8c2347c8d632', false, true),
      -- Chic Retrô clothes
      ('e1f2g3h4-k789-0ghi-e45l-g789h0i123j7', 'FARM', NULL, 'DRESS', NULL, 'amarelo', CURRENT_TIMESTAMP, 'Vestido de verão', 'algodão', 'FEMALE', 'Vestido FARM', 30000, 'M', NULL, '8a3d0b3f-ce6c-4b18-b1d9-9d3456d9e743', false, false),
      ('e2g3h4i5-l890-1hij-f56m-h890i1j234k8', 'OTHER', 'Grife Exclusiva', 'T_SHIRT', NULL, 'rosa', CURRENT_TIMESTAMP, 'Camiseta fashion', 'seda', 'FEMALE', 'Camiseta Rosa', 14000, 'S', NULL, '8a3d0b3f-ce6c-4b18-b1d9-9d3456d9e743', false, false),
      ('e3h4i5j6-m901-2ijk-g67n-i901j2k345l9', 'HERING', NULL, 'PANTS', NULL, 'bege', CURRENT_TIMESTAMP, 'Calça casual', 'poliéster', 'MALE',  'Calça Hering', 19000, 'N_42', NULL, '8a3d0b3f-ce6c-4b18-b1d9-9d3456d9e743', false, false),
      ('e4f5g6h7-k789-0hij-5m7n-g890h1i234j8', 'FARM', NULL, 'DRESS', NULL, 'branco', CURRENT_TIMESTAMP, 'Vestido elegante', 'linho', 'FEMALE', 'Vestido FARM Branco', 31000, 'L', NULL, '8a3d0b3f-ce6c-4b18-b1d9-9d3456d9e743', false, true),
      ('e5g6h7i8-l890-1ijk-6n8o-h901i2j345k9', 'OTHER', 'Design Retrô', 'PANTS', NULL, 'vinho', CURRENT_TIMESTAMP, 'Calça estilo anos 70', 'veludo', 'MALE', 'Calça Vinho Retrô', 20000, 'N_42', NULL, '8a3d0b3f-ce6c-4b18-b1d9-9d3456d9e743', false, true),
      ('e6h7i8j9-m901-2jkl-7o9p-i012j3k456l0', 'HERING', NULL, 'T_SHIRT', NULL, 'mostarda', CURRENT_TIMESTAMP, 'Camiseta vintage', 'algodão', 'UNISEX', 'Camiseta Mostarda', 13000, 'M', NULL, '8a3d0b3f-ce6c-4b18-b1d9-9d3456d9e743', false, true),
      -- Urban Wear clothes
      ('f1g2h3i4-n012-3jkl-h78o-j012k3l456m0', 'NIKE', NULL, 'JACKET_HOODIE', NULL, 'azul marinho', CURRENT_TIMESTAMP, 'Moletom esportivo', 'algodão', 'MALE', 'Moletom Nike', 18000, 'L', NULL, '9b4e1c4f-de7d-4c29-c2e0-0e4567d0f854', false, false),
      ('f2h3i4j5-o123-4klm-i89p-k123l4m567n1', 'RENNER', NULL, 'SHORTS', NULL, 'vermelho', CURRENT_TIMESTAMP, 'Short casual', 'jeans', 'UNISEX', 'Short Renner', 10000, 'M', NULL, '9b4e1c4f-de7d-4c29-c2e0-0e4567d0f854', false, false),
      ('f3i4j5k6-p234-5lmn-j90q-l234m5n678o2', 'OTHER', 'Confecção Local', 'SUIT', 'Macacão', 'verde oliva', CURRENT_TIMESTAMP, 'Macacão estiloso', 'sarja', 'FEMALE', 'Macacão Verde', 22000, 'N_40', NULL, '9b4e1c4f-de7d-4c29-c2e0-0e4567d0f854', false, false),
      ('f4g5h6i7-n012-3klm-8p0q-j123k4l567m1', 'NIKE', NULL, 'SHORTS', NULL, 'azul claro', CURRENT_TIMESTAMP, 'Short urbano', 'nylon', 'MALE', 'Short Azul Claro', 9500, 'M', NULL, '9b4e1c4f-de7d-4c29-c2e0-0e4567d0f854', false, true),
      ('f5h6i7j8-o123-4lmn-9q1r-k234l5m678n2', 'RENNER', NULL, 'PANTS', NULL, 'preto', CURRENT_TIMESTAMP, 'Calça cargo urbana', 'sarja', 'FEMALE', 'Calça Cargo Renner', 21000, 'N_40', NULL, '9b4e1c4f-de7d-4c29-c2e0-0e4567d0f854', false, true),
      ('f6i7j8k9-p234-5mno-0r2s-l345m6n789o3', 'OTHER', 'Cultura de Rua', 'JACKET_HOODIE', NULL, 'laranja queimado', CURRENT_TIMESTAMP, 'Jaqueta urbana', 'algodão', 'UNISEX', 'Jaqueta Street', 23000, 'L', NULL, '9b4e1c4f-de7d-4c29-c2e0-0e4567d0f854', false, true),
      -- Eco Brechó clothes
      ('g1h2i3j4-q345-6mno-k01r-m345n6o789p3', 'FARM', NULL, 'DRESS', NULL, 'verde claro', CURRENT_TIMESTAMP, 'Vestido ecológico', 'algodão orgânico', 'FEMALE',  'Vestido Eco', 28000, 'M', NULL, 'af5f2d5f-ef8e-4d39-d3f1-1f5678e1f965', false, false),
      ('g2i3j4k5-r456-7nop-l12s-n456o7p890q4', 'CEA', NULL, 'T_SHIRT', NULL, 'cinza mescla', CURRENT_TIMESTAMP, 'Camiseta ecológica', 'algodão', 'UNISEX', 'Camiseta Eco', 13000, 'L', NULL, 'af5f2d5f-ef8e-4d39-d3f1-1f5678e1f965', false, false),
      ('g3j4k5l6-s567-8opq-m23t-o567p8q901r5', 'OTHER', 'Marca Sustentável', 'PANTS', NULL, 'marrom', CURRENT_TIMESTAMP, 'Calça eco-friendly', 'linho', 'MALE', 'Calça Sustentável', 19000, 'N_38', NULL, 'af5f2d5f-ef8e-4d39-d3f1-1f5678e1f965', false, false);

INSERT INTO edna.clothe_image (id, created_at, deleted, url, clothe_id) VALUES
-- Multiple images
('img-01', CURRENT_TIMESTAMP, false, 'mock-camiseta.png', 'c1a2b3c4-d123-4abc-8e9f-a123b4c567d1'),
('img-02', CURRENT_TIMESTAMP, false, 'mock-camiseta-2.jpg', 'c1a2b3c4-d123-4abc-8e9f-a123b4c567d1'),
('img-03', CURRENT_TIMESTAMP, false, 'mock-camiseta-3.jpg', 'c1a2b3c4-d123-4abc-8e9f-a123b4c567d1'),
('img-04', CURRENT_TIMESTAMP, false, 'mock-vestido.png', 'c2b3d4e5-f234-5bcd-9f0g-b234c5d678e2'),
('img-05', CURRENT_TIMESTAMP, false, 'mock-vestido-2.png', 'c2b3d4e5-f234-5bcd-9f0g-b234c5d678e2'),
('img-06', CURRENT_TIMESTAMP, false, 'mock-vestido-3.png', 'c2b3d4e5-f234-5bcd-9f0g-b234c5d678e2'),
('img-07', CURRENT_TIMESTAMP, false, 'mock-calca.png', 'c3d4e5f6-g345-6cde-af1h-c345d6e789f3'),
('img-08', CURRENT_TIMESTAMP, false, 'mock-calca-2.png', 'c3d4e5f6-g345-6cde-af1h-c345d6e789f3'),
('img-09', CURRENT_TIMESTAMP, false, 'mock-calca3.png', 'c3d4e5f6-g345-6cde-af1h-c345d6e789f3'),
('img-10', CURRENT_TIMESTAMP, false, 'mock-moletom.png', 'f1g2h3i4-n012-3jkl-h78o-j012k3l456m0'),
('img-11', CURRENT_TIMESTAMP, false, 'mock-moletom-2.png', 'f1g2h3i4-n012-3jkl-h78o-j012k3l456m0'),
('img-12', CURRENT_TIMESTAMP, false, 'mock-moletom-3.png', 'f1g2h3i4-n012-3jkl-h78o-j012k3l456m0'),
('img-13', CURRENT_TIMESTAMP, false, 'mock-camiseta-2.jpg', 'c4d5e6f7-e123-4def-9g1h-a234b5c678d2'),
('img-14', CURRENT_TIMESTAMP, false, 'mock-calca-2.png', 'c5e6f7g8-f234-5efg-0h2i-b345c6d789e3'),
('img-15', CURRENT_TIMESTAMP, false, 'mock-moletom-2.png', 'c6f7g8h9-g345-6fgh-1i3j-c456d7e890f4'),
('img-16', CURRENT_TIMESTAMP, false, 'mock-shorts.png', 'd1e2f3g4-h456-7def-b12i-d456e7f890g4'),
('img-17', CURRENT_TIMESTAMP, false, 'mock-moletom-3.png', 'd2f3g4h5-i567-8efg-c23j-e567f8g901h5'),
('img-18', CURRENT_TIMESTAMP, false, 'mock-moletom.png', 'd3g4h5i6-j678-9fgh-d34k-f678g9h012i6'),
('img-19', CURRENT_TIMESTAMP, false, 'mock-shorts-2.png', 'd4e5f6g7-h456-7ghi-2j4k-d567e8f901g5'),
('img-20', CURRENT_TIMESTAMP, false, 'mock-vestido-2.png', 'd5f6g7h8-i567-8hij-3k5l-e678f9g012h6'),
('img-21', CURRENT_TIMESTAMP, false, 'mock-camiseta.png', 'd6g7h8i9-j678-9ijk-4l6m-f789g0h123i7'),
('img-22', CURRENT_TIMESTAMP, false, 'mock-vestido.png', 'e1f2g3h4-k789-0ghi-e45l-g789h0i123j7'),
('img-23', CURRENT_TIMESTAMP, false, 'mock-camiseta-3.jpg', 'e2g3h4i5-l890-1hij-f56m-h890i1j234k8'),
('img-24', CURRENT_TIMESTAMP, false, 'mock-calca.png', 'e3h4i5j6-m901-2ijk-g67n-i901j2k345l9'),
('img-25', CURRENT_TIMESTAMP, false, 'mock-vestido-3.png', 'e4f5g6h7-k789-0hij-5m7n-g890h1i234j8'),
('img-26', CURRENT_TIMESTAMP, false, 'mock-calca3.png', 'e5g6h7i8-l890-1ijk-6n8o-h901i2j345k9'),
('img-27', CURRENT_TIMESTAMP, false, 'mock-camiseta-2.jpg', 'e6h7i8j9-m901-2jkl-7o9p-i012j3k456l0'),
('img-28', CURRENT_TIMESTAMP, false, 'mock-shorts.png', 'f2h3i4j5-o123-4klm-i89p-k123l4m567n1'),
('img-29', CURRENT_TIMESTAMP, false, 'mock-suit.png', 'f3i4j5k6-p234-5lmn-j90q-l234m5n678o2'),
('img-30', CURRENT_TIMESTAMP, false, 'mock-shorts-2.png', 'f4g5h6i7-n012-3klm-8p0q-j123k4l567m1'),
('img-31', CURRENT_TIMESTAMP, false, 'mock-calca-2.png', 'f5h6i7j8-o123-4lmn-9q1r-k234l5m678n2'),
('img-32', CURRENT_TIMESTAMP, false, 'mock-moletom-3.png', 'f6i7j8k9-p234-5mno-0r2s-l345m6n789o3'),
('img-33', CURRENT_TIMESTAMP, false, 'mock-vestido-2.png', 'g1h2i3j4-q345-6mno-k01r-m345n6o789p3'),
('img-34', CURRENT_TIMESTAMP, false, 'mock-camiseta.png', 'g2i3j4k5-r456-7nop-l12s-n456o7p890q4'),
('img-35', CURRENT_TIMESTAMP, false, 'mock-calca.png', 'g3j4k5l6-s567-8opq-m23t-o567p8q901r5');


INSERT INTO edna.address (id, cep, city, created_at, neighborhood, "number", street, store_id)
VALUES
    ('5db49ab1-f98a-44f1-bf5c-e6d92d6974e0', '88010000', 'Florianópolis', NOW(), 'Centro', 350, 'Rua Felipe Schmidt', '6e1f8c1e-d229-4e98-99c2-7a6239a8b531'),
    ('ba94d9de-93a2-4f75-b07d-82b0e59d1975', '88025000', 'Florianópolis', NOW(), 'Trindade', 561, 'Rua Lauro Linhares', '7f2c9a2e-bd5b-4a09-a3b8-8c2347c8d632'),
    ('e8507b9f-b177-453f-91db-85039473b32f', '88037001', 'Florianópolis', NOW(), 'Itacorubi', 2024, 'Rodovia Admar Gonzaga', '8a3d0b3f-ce6c-4b18-b1d9-9d3456d9e743'),
    ('a32c9e4f-6f3b-4a2e-88b1-1a5d6b0f342e', '88062000', 'Florianópolis', NOW(), 'Lagoa da Conceição', 150, 'Avenida das Rendeiras', '9b4e1c4f-de7d-4c29-c2e0-0e4567d0f854'),
    ('f7c38fbe-cf65-42fc-a0fc-e9b0e0b4db7d', '88062000', 'Florianópolis', NOW(), 'Lagoa da Conceição', 3790, 'Avenida das Rendeiras', 'af5f2d5f-ef8e-4d39-d3f1-1f5678e1f965');


INSERT INTO edna.store_day_schedule (
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
    ('9a8f67e1-f7be-4d72-89bb-e91e04989d9c', 1080, 5, 480, 'af5f2d5f-ef8e-4d39-d3f1-1f5678e1f965', true);


insert into edna.favorite_store (customer_id, store_id)
values
    ('d290f1ee-6c54-4b01-90e6-d701748f0851', '7f2c9a2e-bd5b-4a09-a3b8-8c2347c8d632'),
    ('d290f1ee-6c54-4b01-90e6-d701748f0851', '9b4e1c4f-de7d-4c29-c2e0-0e4567d0f854');

-- Brechó Bella clothes
INSERT INTO edna.clothe_order (id, created_at, status, clothe_id, customer_id, store_id, is_first_order, rating) VALUES
    ('order-bb1', CURRENT_TIMESTAMP, 'COMPLETED', 'c4d5e6f7-e123-4def-9g1h-a234b5c678d2', 'd290f1ee-6c54-4b01-90e6-d701748f0851', '6e1f8c1e-d229-4e98-99c2-7a6239a8b531', false, 5),
    ('order-bb2', CURRENT_TIMESTAMP, 'COMPLETED', 'c5e6f7g8-f234-5efg-0h2i-b345c6d789e3', '2a6fa6dd-8b1f-4d3d-97cb-20adcf11cc74', '6e1f8c1e-d229-4e98-99c2-7a6239a8b531', false, 4),
    ('order-bb3', CURRENT_TIMESTAMP, 'COMPLETED', 'c6f7g8h9-g345-6fgh-1i3j-c456d7e890f4', '3b9f0b7c-a7e2-47c5-bc88-9f2522d6e78e', '6e1f8c1e-d229-4e98-99c2-7a6239a8b531', false, 3),
-- Vintage Style clothes
    ('order-vs1', CURRENT_TIMESTAMP, 'COMPLETED', 'd4e5f6g7-h456-7ghi-2j4k-d567e8f901g5', 'd290f1ee-6c54-4b01-90e6-d701748f0851', '7f2c9a2e-bd5b-4a09-a3b8-8c2347c8d632', false, 5),
    ('order-vs2', CURRENT_TIMESTAMP, 'COMPLETED', 'd5f6g7h8-i567-8hij-3k5l-e678f9g012h6', '4c8e1d08-5ae7-4b66-8c6b-563d44f872ed', '7f2c9a2e-bd5b-4a09-a3b8-8c2347c8d632', false, 2),
    ('order-vs3', CURRENT_TIMESTAMP, 'COMPLETED', 'd6g7h8i9-j678-9ijk-4l6m-f789g0h123i7', '5d7d9e6f-8c4d-4c32-b0d5-372f9eb7fd43', '7f2c9a2e-bd5b-4a09-a3b8-8c2347c8d632', false, 3),
-- Chic Retrô clothes
    ('order-cr1', CURRENT_TIMESTAMP, 'COMPLETED', 'e4f5g6h7-k789-0hij-5m7n-g890h1i234j8', 'd290f1ee-6c54-4b01-90e6-d701748f0851', '8a3d0b3f-ce6c-4b18-b1d9-9d3456d9e743', false, 4),
    ('order-cr2', CURRENT_TIMESTAMP, 'COMPLETED', 'e5g6h7i8-l890-1ijk-6n8o-h901i2j345k9', '2a6fa6dd-8b1f-4d3d-97cb-20adcf11cc74', '8a3d0b3f-ce6c-4b18-b1d9-9d3456d9e743', false, 2),
    ('order-cr3', CURRENT_TIMESTAMP, 'COMPLETED', 'e6h7i8j9-m901-2jkl-7o9p-i012j3k456l0', '3b9f0b7c-a7e2-47c5-bc88-9f2522d6e78e', '8a3d0b3f-ce6c-4b18-b1d9-9d3456d9e743', false, 1),
-- Urban Wear clothes
    ('order-uw1', CURRENT_TIMESTAMP, 'COMPLETED', 'f4g5h6i7-n012-3klm-8p0q-j123k4l567m1', '4c8e1d08-5ae7-4b66-8c6b-563d44f872ed', '9b4e1c4f-de7d-4c29-c2e0-0e4567d0f854', false, 5),
    ('order-uw2', CURRENT_TIMESTAMP, 'COMPLETED', 'f5h6i7j8-o123-4lmn-9q1r-k234l5m678n2', '5d7d9e6f-8c4d-4c32-b0d5-372f9eb7fd43', '9b4e1c4f-de7d-4c29-c2e0-0e4567d0f854', false, 4),
    ('order-uw3', CURRENT_TIMESTAMP, 'COMPLETED', 'f6i7j8k9-p234-5mno-0r2s-l345m6n789o3', '2a6fa6dd-8b1f-4d3d-97cb-20adcf11cc74', '9b4e1c4f-de7d-4c29-c2e0-0e4567d0f854', false, 5);


INSERT INTO edna.store_image (id, created_at, deleted, url, type, store_id) VALUES
-- Multiple images
('s-img-04', CURRENT_TIMESTAMP, false, 'mock-female-store.png', 'BANNER', '6e1f8c1e-d229-4e98-99c2-7a6239a8b531');
