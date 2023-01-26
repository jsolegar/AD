connect 'jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2;create=true';
drop table image;
drop table usuarios;

create table usuarios (
                id_usuario varchar (256) primary key,
                password varchar (256)
);


create table image (
        id              int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
        title           varchar (256) NOT NULL,
        description     varchar (1024) NOT NULL,
        keywords        varchar (256) NOT NULL,
        author          varchar (256) NOT NULL, /* Original author of the image */
        creator                 varchar (256) NOT NULL, /* User inserting information in db */
        capture_date    varchar (10) NOT NULL,  /* Format AAAA/MM/DD asked to the user*/
        storage_date    varchar (10) NOT NULL,  /* Format AAAA/MM/DD filled automatically when stored */
        filename        varchar (2048) NOT NULL, /* Only the name of the file, directory is fixed by the Web Application*/
        primary key (id) ,
                foreign key (creator) references usuarios(id_usuario)
);
disconnect;

