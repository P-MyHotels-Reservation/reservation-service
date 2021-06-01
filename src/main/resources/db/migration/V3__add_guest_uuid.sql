ALTER TABLE reservation
ADD guest_uuid varchar(36);

ALTER TABLE reservation
DROP guest_id;
