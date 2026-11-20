import { createServer } from 'node:http';
import { createYoga, createSchema } from 'graphql-yoga';
import mysql from 'mysql2/promise';

const db = mysql.createPool({
  host: process.env.DB_HOST || '127.0.0.1',
  port: Number(process.env.DB_PORT || 3306),
  user: process.env.DB_USER || 'root',
  password: process.env.DB_PASSWORD || '',
  database: process.env.DB_DATABASE || 'db_multas',
  waitForConnections: true,
  connectionLimit: 5
});

const yoga = createYoga({
  schema: createSchema({
    typeDefs: /* GraphQL */ `
      type Multa {
        id: ID!
        codigo: String!
        ci_conductor: String!
        nombre_conductor: String!
        fecha: String!
        tipo_infraccion: String!
        lugar: String!
        monto: Float!
        estado: String!
      }
      type Query {
        multa(codigo: String, ci: String): [Multa!]!
        pagable(codigo: String!, ci: String!): Boolean!
      }
    `,
    resolvers: {
      Query: {
        multa: async (_, { codigo, ci }) => {
          if (Boolean(codigo) === Boolean(ci)) {
            throw new Error('Indique exactamente uno: codigo o ci');
          }
          const columna = codigo ? 'codigo' : 'ci_conductor';
          const [rows] = await db.execute(
            `SELECT id, codigo, ci_conductor, nombre_conductor, fecha,
                    tipo_infraccion, lugar, monto, estado
             FROM multas WHERE ${columna} = ? ORDER BY id DESC`,
            [codigo || ci]
          );
          return rows.map(row => ({
            ...row,
            fecha: row.fecha instanceof Date ? row.fecha.toISOString().slice(0, 10) : String(row.fecha),
            monto: Number(row.monto)
          }));
        },
        pagable: async (_, { codigo, ci }) => {
          const [rows] = await db.execute(
            "SELECT id FROM multas WHERE codigo = ? AND ci_conductor = ? AND estado = 'pendiente' LIMIT 1",
            [codigo, ci]
          );
          return rows.length === 1;
        }
      }
    }
  })
});

const port = Number(process.env.PORT || 4000);
createServer(yoga).listen(port, () => {
  console.log(`GraphQL: http://localhost:${port}/graphql`);
});
