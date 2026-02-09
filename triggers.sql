/*trigger za vozila*/
CREATE OR REPLACE FUNCTION public.trg_logs_vozila_fn()
RETURNS trigger AS $$
BEGIN
  IF TG_OP = 'INSERT' THEN
    INSERT INTO public.logs(tabela, datum, akcija, uporabnik_id)
    VALUES ('vozila', CURRENT_DATE, 'INSERT vozilo id=' || NEW.id, NEW.odgovorni_uporabnik_id);

    RETURN NEW;

  ELSIF TG_OP = 'UPDATE' THEN
    INSERT INTO public.logs(tabela, datum, akcija, uporabnik_id)
    VALUES ('vozila', CURRENT_DATE, 'UPDATE vozilo id=' || NEW.id, NEW.odgovorni_uporabnik_id);

    RETURN NEW;

  ELSIF TG_OP = 'DELETE' THEN
    INSERT INTO public.logs(tabela, datum, akcija, uporabnik_id)
    VALUES ('vozila', CURRENT_DATE, 'DELETE vozilo id=' || OLD.id, OLD.odgovorni_uporabnik_id);

    RETURN OLD;
  END IF;

  RETURN NULL;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trg_logs_vozila
AFTER INSERT OR UPDATE OR DELETE ON public.vozila
FOR EACH ROW
EXECUTE FUNCTION public.trg_logs_vozila_fn();

/*trigger za servis*/
CREATE OR REPLACE FUNCTION public.trg_logs_servisi_fn()
RETURNS trigger AS $$
BEGIN
  IF TG_OP = 'INSERT' THEN
    INSERT INTO public.logs(tabela, datum, akcija, uporabnik_id)
    VALUES ('servisi', CURRENT_DATE, 'INSERT servis id=' || NEW.id || ' vozilo_id=' || COALESCE(NEW.vozilo_id::text,'NULL'),
            NEW.uporabnik_id);

    RETURN NEW;

  ELSIF TG_OP = 'UPDATE' THEN
    INSERT INTO public.logs(tabela, datum, akcija, uporabnik_id)
    VALUES ('servisi', CURRENT_DATE, 'UPDATE servis id=' || NEW.id || ' vozilo_id=' || COALESCE(NEW.vozilo_id::text,'NULL'),
            NEW.uporabnik_id);

    RETURN NEW;

  ELSIF TG_OP = 'DELETE' THEN
    INSERT INTO public.logs(tabela, datum, akcija, uporabnik_id)
    VALUES ('servisi', CURRENT_DATE, 'DELETE servis id=' || OLD.id || ' vozilo_id=' || COALESCE(OLD.vozilo_id::text,'NULL'),
            OLD.uporabnik_id);

    RETURN OLD;
  END IF;

  RETURN NULL;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trg_logs_servisi
AFTER INSERT OR UPDATE OR DELETE ON public.servisi
FOR EACH ROW
EXECUTE FUNCTION public.trg_logs_servisi_fn();
/*trigger za stroske*/

CREATE OR REPLACE FUNCTION public.trg_logs_stroski_fn()
RETURNS trigger AS $$
BEGIN
  IF TG_OP = 'INSERT' THEN
    INSERT INTO public.logs(tabela, datum, akcija, uporabnik_id)
    VALUES ('stroski', CURRENT_DATE,
            'INSERT strosek id=' || NEW.id ||
            ' vozilo_id=' || COALESCE(NEW.vozilo_id::text,'NULL') ||
            ' servis_id=' || COALESCE(NEW.servis_id::text,'NULL'),
            NEW.uporabnik_id);

    RETURN NEW;

  ELSIF TG_OP = 'UPDATE' THEN
    INSERT INTO public.logs(tabela, datum, akcija, uporabnik_id)
    VALUES ('stroski', CURRENT_DATE,
            'UPDATE strosek id=' || NEW.id ||
            ' vozilo_id=' || COALESCE(NEW.vozilo_id::text,'NULL') ||
            ' servis_id=' || COALESCE(NEW.servis_id::text,'NULL'),
            NEW.uporabnik_id);

    RETURN NEW;

  ELSIF TG_OP = 'DELETE' THEN
    INSERT INTO public.logs(tabela, datum, akcija, uporabnik_id)
    VALUES ('stroski', CURRENT_DATE,
            'DELETE strosek id=' || OLD.id ||
            ' vozilo_id=' || COALESCE(OLD.vozilo_id::text,'NULL') ||
            ' servis_id=' || COALESCE(OLD.servis_id::text,'NULL'),
            OLD.uporabnik_id);

    RETURN OLD;
  END IF;

  RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_logs_stroski
AFTER INSERT OR UPDATE OR DELETE ON public.stroski
FOR EACH ROW
EXECUTE FUNCTION public.trg_logs_stroski_fn();
