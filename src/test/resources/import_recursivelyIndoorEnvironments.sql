-- nível 1 (root)
INSERT INTO TB_INDOOR_ENVIRONMENT (
    id,
    name,
    beacons_signal_statistics,
    parent_id,
    parent_organization_id
)
VALUES (
    200,
    'root',
    '[]'::jsonb,
    NULL,
    101
);

-- nível 2
INSERT INTO TB_INDOOR_ENVIRONMENT (
    id,
    name,
    beacons_signal_statistics,
    parent_id,
    parent_organization_id
)
VALUES (
    201,
    'child1',
    '[]'::jsonb,
    200,
    101
);

-- nível 3
INSERT INTO TB_INDOOR_ENVIRONMENT (
    id,
    name,
    beacons_signal_statistics,
    parent_id,
    parent_organization_id
)
VALUES (
    202,
    'child2',
    '[]'::jsonb,
    201,
    101
);

-- nível 4
INSERT INTO TB_INDOOR_ENVIRONMENT (
    id,
    name,
    beacons_signal_statistics,
    parent_id,
    parent_organization_id
)
VALUES (
    203,
    'child3',
    '[]'::jsonb,
    202,
    101
);

-- nível 5
INSERT INTO TB_INDOOR_ENVIRONMENT (
    id,
    name,
    beacons_signal_statistics,
    parent_id,
    parent_organization_id
)
VALUES (
    204,
    'child4',
    '[]'::jsonb,
    203,
    101
);
