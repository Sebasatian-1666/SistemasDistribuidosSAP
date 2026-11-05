const express = require('express');
const { graphqlHTTP } = require('express-graphql');
const { buildSchema } = require('graphql');

const schema = buildSchema(`
    type Persona {
        CI: String
        Nombres: String
        PrimerApellido: String
        SegundoApellido: String
    }

    type Query {
        buscarPorCI(CI: String!): Persona
    }
`);

const personas = [
    { CI: '1234567', Nombres: 'Juan Carlos', PrimerApellido: 'Perez', SegundoApellido: 'Gomez' },
    { CI: '7654321', Nombres: 'Maria Elena', PrimerApellido: 'Lopez', SegundoApellido: 'Martinez' },
    { CI: '1111111', Nombres: 'Pedro', PrimerApellido: 'Rojas', SegundoApellido: 'Fernandez' },
    { CI: '2222222', Nombres: 'Ana Lucia', PrimerApellido: 'Mendoza', SegundoApellido: 'Vargas' },
];

const root = {
    buscarPorCI: ({ CI }) => {
        return personas.find(p => p.CI === CI) || null;
    }
};

const app = express();

app.use('/graphql', graphqlHTTP({
    schema: schema,
    rootValue: root,
    graphiql: true
}));

app.listen(4000, () => {
    console.log('SEGIP GraphQL corriendo en http://localhost:4000/graphql');
});
