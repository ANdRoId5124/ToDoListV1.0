CREATE TABLE to_do_lists
(
    list_id serial PRIMARY KEY NOT NULL,
    name text NOT NULL,
    topic text NOT NULL

);


CREATE TABLE tasks
(
    list_id int NOT NULL,
    task text NOT NULL,
    condition text DEFAULT 'active' NOT NULL,
    FOREIGN KEY (list_id) REFERENCES to_do_lists(list_id)
);

ALTER TABLE tasks
    ADD CONSTRAINT CHK_tasks_condition CHECK ((condition = 'active' or condition = 'complite'));