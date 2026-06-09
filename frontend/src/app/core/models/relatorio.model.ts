export interface RelatorioItem {
  livroCodigo: number;
  titulo: string;
  editora?: string;
  edicao?: number;
  anoPublicacao?: string;
  valor: number;
  assuntos?: string;
}

export interface RelatorioAutor {
  autorCodigo: number;
  autorNome: string;
  livros: RelatorioItem[];
  quantidadeLivros: number;
  valorTotal: number;
}
