class Result {
    constructor(id, val_temp, val_hum, val_sol, date_resultat, serie, project_id) {
        this.id = id;
        this.val_temp = val_temp;
        this.val_hum = val_hum;
        this.val_sol = val_sol;
        this.date_resultat = date_resultat;
        this.serie = serie;
        this.project_id = project_id;
    }
}

module.exports = Result;
